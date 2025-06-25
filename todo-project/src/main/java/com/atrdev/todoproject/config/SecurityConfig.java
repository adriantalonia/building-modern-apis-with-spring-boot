package com.atrdev.todoproject.config;

import com.atrdev.todoproject.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor for SecurityConfig.
     *
     * @param userRepository          Repository for user data access
     * @param jwtAuthenticationFilter Custom filter for JWT authentication
     */
    public SecurityConfig(UserRepository userRepository, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userRepository = userRepository;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the custom UserDetailsService.
     *
     * @return UserDetailsService implementation that loads users by email
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Configures the password encoder (BCrypt).
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the AuthenticationManager bean.
     *
     * @param config AuthenticationConfiguration
     * @return AuthenticationManager instance
     * @throws Exception if authentication manager cannot be obtained
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures the authentication entry point (triggered when authentication fails).
     *
     * @return AuthenticationEntryPoint that returns JSON responses
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());  // 401 for authentication failures
            response.setHeader("WWW-Authenticate", "");  // Prevents browser auth dialogs
            response.getWriter().write("""
                    {
                        "status": 401,
                        "error": "Unauthorized",
                        "message": "Authentication required",
                        "path": "%s"
                    }
                    """.formatted(request.getRequestURI()));
        };
    }

    /**
     * Configures the access denied handler (triggered when authorization fails).
     *
     * @return AccessDeniedHandler that returns JSON responses
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpStatus.FORBIDDEN.value());  // 403 for authorization failures
            response.setHeader("WWW-Authenticate", "");  // Prevents browser auth dialogs
            response.getWriter().write("""
                    {
                        "status": 403,
                        "error": "Forbidden",
                        "message": "Insufficient permissions",
                        "path": "%s"
                    }
                    """.formatted(request.getRequestURI()));
        };
    }

    /**
     * Configures the security filter chain.
     *
     * @param http HttpSecurity configuration
     * @return Configured SecurityFilterChain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure authorization rules
        http.authorizeHttpRequests(
                configurer ->
                        configurer.requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**",
                                        "/swagger-resources/**", "/webjars/**", "/docs")
                                .permitAll()  // Public endpoints
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated());  // All other endpoints require authentication

        // Disable CSRF as we're using JWT (stateless)
        http.csrf(csrf -> csrf.disable());

        // Configure exception handling
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint())  // Handles authentication failures
                        .accessDeniedHandler(accessDeniedHandler()));  // Handles authorization failures

        // Set stateless session policy (JWT doesn't need sessions)
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT filter before the default username/password filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

package com.atrdev.todoproject.service;

import com.atrdev.todoproject.model.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FindAuthenticatedUserImpl implements FindAuthenticatedUser {

    @Override
    public User getAuthenticatedUser() {
        // Get the current authentication context from Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Validate the authentication state
        if (isAuthenticationInvalid(authentication)) {
            // throw new AccessDeniedException("Authentication required"); 403 FORBIDDEN
            throw new AuthenticationCredentialsNotFoundException("Authentication required"); // 401
        }
        // The principal should be our custom User object at this point
        return extractAuthenticatedUser(authentication);
    }

    /**
     * Checks if the authentication is invalid or represents an anonymous user
     *
     * @param authentication the authentication object to validate
     * @return true if authentication is invalid, false otherwise
     */
    private boolean isAuthenticationInvalid(Authentication authentication) {
        return authentication == null ||
                !authentication.isAuthenticated() ||
                isAnonymousUser(authentication);
    }

    /**
     * Determines if the authentication represents an anonymous/unauthenticated user
     *
     * @param authentication the authentication object to check
     * @return true if the user is anonymous, false otherwise
     */
    private boolean isAnonymousUser(Authentication authentication) {
        return "anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * Extracts the User object from the authentication principal
     *
     * @param authentication the validated authentication object
     * @return the User principal
     * @throws ClassCastException if principal is not of expected User type
     */
    private User extractAuthenticatedUser(Authentication authentication) {
        try {
            return (User) authentication.getPrincipal();
        } catch (ClassCastException e) {
            throw new AccessDeniedException("Invalid user principal type", e);
        }
    }
}

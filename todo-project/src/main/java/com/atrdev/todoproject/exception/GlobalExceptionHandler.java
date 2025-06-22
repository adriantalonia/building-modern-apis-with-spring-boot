package com.atrdev.todoproject.exception;

import com.atrdev.todoproject.model.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return buildErrorResponse(ex, resolveHttpStatus(ex.getStatusCode()), ex.getReason(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        String message = "Validation error: " + String.join("; ", errors);
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            Exception ex, WebRequest request) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Authentication failed", request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        // AuthenticationException 401
        // AccessDeniedException 403
        HttpStatus status = ex.getMessage().contains("Authentication") ?
                HttpStatus.UNAUTHORIZED :
                HttpStatus.FORBIDDEN;
        return buildErrorResponse(ex, status, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        String message = isProduction() ? "Internal server error" : ex.getMessage();
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, message, request);
    }

    private HttpStatus resolveHttpStatus(HttpStatusCode statusCode) {
        if (statusCode instanceof HttpStatus) {
            return (HttpStatus) statusCode;
        }
        return HttpStatus.valueOf(statusCode.value());
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception ex,
            HttpStatus status,
            String message,
            WebRequest request) {

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                getRequestPath(request)
        );

        if (!isProduction()) {
            response.setDetails(new ErrorResponse.DebugDetails(
                    ex.getClass().getName(),
                    getStackTrace(ex)
            ));
        }
        return ResponseEntity.status(status).body(response);
    }

    private String getRequestPath(WebRequest request) {
        return request instanceof ServletWebRequest
                ? ((ServletWebRequest) request).getRequest().getRequestURI()
                : "";
    }

    private List<String> getStackTrace(Exception ex) {
        return Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .toList();
    }

    private boolean isProduction() {
        String env = System.getenv("APP_ENV");
        return env != null && env.equals("prod");
    }
}

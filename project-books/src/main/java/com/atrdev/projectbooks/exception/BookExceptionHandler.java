package com.atrdev.projectbooks.exception;

import com.atrdev.projectbooks.model.dto.response.BookErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException ex) {
        BookErrorResponse errorResponse = new BookErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BookErrorResponse> handleException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessages
                    .append("Field: '")
                    .append(error.getField())
                    .append("' - ")
                    .append(error.getDefaultMessage())
                    .append(". ");
        });
        BookErrorResponse errorResponse = new BookErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessages.toString(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

package com.airbnb.property.controller;

import com.airbnb.property.dto.ResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO<Void> handleResourceNotFoundException(RuntimeException ex) {
        String errorMessage = messageSource.getMessage("error.message.request.error", null, LocaleContextHolder.getLocale());
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMessage, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDTO<Void> handleException(Exception ex) {
        String errorMessage = messageSource.getMessage("error.message.server.error", null, LocaleContextHolder.getLocale());
        return new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage, ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO<Void> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        String message = messageSource.getMessage("error.message.request.error", null, LocaleContextHolder.getLocale());
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), message, mapToString(errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO<Void> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(propertyPath, message);
        }
        return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), mapToString(errors));
    }

    private String mapToString(Map<?, ?> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }

        if (!map.isEmpty()) {
            // Remove the trailing comma and space
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}


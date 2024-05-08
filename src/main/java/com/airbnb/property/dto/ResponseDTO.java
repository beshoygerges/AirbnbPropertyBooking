package com.airbnb.property.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ResponseDTO<T> {
    private Integer status = HttpStatus.OK.value();
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String techMessage;
    private T data;

    public ResponseDTO(Integer status, String message, String techMessage) {
        this.status = status;
        this.message = message;
        this.techMessage = techMessage;
    }

    public ResponseDTO(Integer status, String message, String techMessage, T data) {
        this.status = status;
        this.message = message;
        this.techMessage = techMessage;
        this.data = data;
    }
}

package com.hanghae.springboard.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusResponseDto<T> {
    private HttpStatus httpStatus;

    private T data;
    private String message;
    public StatusResponseDto(boolean success, String message) {
        if (success){
            this.httpStatus = HttpStatus.OK;
            this.message = message;
        }
        else{
            this.httpStatus = HttpStatus.BAD_REQUEST;
            this.message = message;
        }

    }
}

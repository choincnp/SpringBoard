package com.hanghae.springboard.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class StatusResponseDto {
    private boolean success;
    private HttpStatus httpStatus;
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

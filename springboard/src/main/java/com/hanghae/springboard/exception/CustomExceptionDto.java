package com.hanghae.springboard.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomExceptionDto {
    private int statusCode;
    private String msg;

    public CustomExceptionDto(CustomException customException){
        statusCode = customException.getErrorCode().getHttpStatus().value();
        msg = customException.getMessage();
    }

}

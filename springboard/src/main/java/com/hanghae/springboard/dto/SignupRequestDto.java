package com.hanghae.springboard.dto;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @Max(value = 10, message = "내가 아이디 10자 밑으로 하라고 했지")
    @Min(value = 4, message = "아이디가 너무 짧잖아 인간적으로")
    @Pattern(regexp = "[a-z0-9]")
    private String username;

    @Max(value = 15)
    @Min(value = 8)
    @Pattern(regexp = "[a-zA-Z0-9]")
    private String password;
}

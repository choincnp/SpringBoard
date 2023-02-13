package com.hanghae.springboard.controller;

import com.hanghae.springboard.dto.LoginRequestDto;
import com.hanghae.springboard.dto.SignupRequestDto;
import com.hanghae.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());}
        userService.signUp(signupRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("success",headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return "success";
    }
}

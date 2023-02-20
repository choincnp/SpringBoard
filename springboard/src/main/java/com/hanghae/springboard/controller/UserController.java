package com.hanghae.springboard.controller;

import com.hanghae.springboard.domain.user.dto.LoginRequestDto;
import com.hanghae.springboard.domain.user.dto.SignupRequestDto;
import com.hanghae.springboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/signup") // 회원가입
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());}
        userService.signUp(signupRequestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok("success");
    }
}

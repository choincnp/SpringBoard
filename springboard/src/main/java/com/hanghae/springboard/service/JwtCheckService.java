package com.hanghae.springboard.service;

import com.hanghae.springboard.entity.User;
import com.hanghae.springboard.exception.CustomException;
import com.hanghae.springboard.exception.ErrorCode;
import com.hanghae.springboard.jwt.JwtUtil;
import com.hanghae.springboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;

@Service
@RequiredArgsConstructor
public abstract class JwtCheckService {
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public User jwtValidation(HttpServletRequest request){ // JWT 인증정보 추가
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
                return userRepository.findByUsername(claims.getSubject()).orElseThrow(()->new CustomException(ErrorCode.EMPTY_CLIENT));
            }throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        else throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }
}

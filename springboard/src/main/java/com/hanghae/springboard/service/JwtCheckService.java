package com.hanghae.springboard.service;

import com.hanghae.springboard.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.parser.Entity;

@Service
@RequiredArgsConstructor
public abstract class JwtCheckService {
    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public String CheckJwtValid(HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        
        if (token != null){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else throw new IllegalArgumentException("Token Error");
            return "";
        }
        else return null;
    }
}

package com.hanghae.springboard.service;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.dto.StatusResponseDto;
import com.hanghae.springboard.entity.Letter;
import com.hanghae.springboard.entity.User;
import com.hanghae.springboard.jwt.JwtUtil;
import com.hanghae.springboard.repository.LetterRepository;
import com.hanghae.springboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public LetterResponseDto postLetter(LetterRequestDto letterRequestDto, HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else throw new IllegalArgumentException("Token Validate Error");

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            Letter letter = letterRepository.saveAndFlush(new Letter(letterRequestDto, user));
            return letterRepository.findById(letter.getId()).map(LetterResponseDto::new).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
        } else return null; // null 던져야 하나? 던지기 싫은데...
    }

    @Transactional(readOnly = true)
    public List<LetterResponseDto> findAll(){ // service Layer에서 Entity >> DTO 변환작업, 사유 : LazyInitializationException 위험부담 줄임
        return letterRepository.findAllByOrderByModifiedAtDesc().stream().map(LetterResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LetterResponseDto findOne(Long id){
        return letterRepository.findById(id).map(LetterResponseDto::new).orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
    }
    @Transactional
    public ResponseEntity<?> modifyLetter(Long id, LetterRequestDto letterRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims; // 작성자 확인 넣기
        if (token != null ){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else throw new IllegalArgumentException("Token Validate Error");
            if(userRepository.findById(id).equals(userRepository.findByUsername(claims.getSubject()))){
                Letter letter = letterRepository.findById(id).orElseThrow(IllegalArgumentException::new);
                letter.update(letterRequestDto);
            }return ResponseEntity.ok(letterRepository.findById(id).map(LetterResponseDto::new));
        } else return null;
    }
    @Transactional
    public ResponseEntity<?> deleteLetter(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims; // 작성자 확인 넣기
        if (token != null ){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else throw new IllegalArgumentException("Token Validate Error");
            if(userRepository.findById(id).equals(userRepository.findByUsername(claims.getSubject()))){
                Letter letter = letterRepository.findById(id).orElseThrow(IllegalArgumentException::new);
                letterRepository.delete(letter);
            }return ResponseEntity.ok("게시글 삭제 성공");
        } else return null;
    }

}

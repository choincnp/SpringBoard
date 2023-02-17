package com.hanghae.springboard.service;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.dto.StatusResponseDto;
import com.hanghae.springboard.entity.Letter;
import com.hanghae.springboard.entity.User;
import com.hanghae.springboard.entity.UserRoleEnum;
import com.hanghae.springboard.exception.CustomException;
import com.hanghae.springboard.exception.ErrorCode;
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
        User user = jwtValidation(request);
        Letter letter = letterRepository.saveAndFlush(new Letter(letterRequestDto,user));
        return letterRepository.findById(letter.getId()).map(LetterResponseDto::new).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
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
        User user = jwtValidation(request);
        Letter letter = letterRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (user.getUsername().equals(letter.getUser().getUsername()))letter.update(letterRequestDto);
        else if (user.getRole().equals(UserRoleEnum.ADMIN)) letter.update(letterRequestDto);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);
        return ResponseEntity.ok(letterRepository.findById(id).map(LetterResponseDto::new));
    }
    @Transactional
    public ResponseEntity<?> deleteLetter(Long id, HttpServletRequest request) {
        User user = jwtValidation(request);
        Letter letter = letterRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (user.getUsername().equals(letter.getUser().getUsername())) letterRepository.delete(letter);
        else if (user.getRole().equals(UserRoleEnum.ADMIN)) letterRepository.delete(letter);
        return ResponseEntity.ok("게시글 삭제 성공");
    }

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

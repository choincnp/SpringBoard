package com.hanghae.springboard.service;

import com.hanghae.springboard.dto.CommentRequestDto;
import com.hanghae.springboard.dto.CommentResponseDto;
import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.entity.Comment;
import com.hanghae.springboard.entity.Letter;
import com.hanghae.springboard.entity.User;
import com.hanghae.springboard.exception.CustomException;
import com.hanghae.springboard.exception.ErrorCode;
import com.hanghae.springboard.jwt.JwtUtil;
import com.hanghae.springboard.repository.CommentRepository;
import com.hanghae.springboard.repository.LetterRepository;
import com.hanghae.springboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final LetterRepository letterRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<?> addComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        User user = jwtValidation(request);
        Letter letter = letterRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_DATA));// 게시글의 저장 유무
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,letter, user));
        return ResponseEntity.ok(commentRepository.findById(comment.getId()).map(CommentResponseDto::new));
    }
    @Transactional
    public ResponseEntity<?> modifyComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        User user = jwtValidation(request);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_DATA));
        if (user.getId().equals(comment.getUser().getId())) comment.update(commentRequestDto);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);
        return ResponseEntity.ok(commentRepository.findById(comment.getId()).map(CommentResponseDto::new));
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, HttpServletRequest request){
        User user = jwtValidation(request);
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_DATA));
        if (user.getId().equals(comment.getUser().getId())) commentRepository.delete(comment);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);
        return ResponseEntity.ok("댓글 삭제 성공");
    }

    public User jwtValidation(HttpServletRequest request){ // JWT 인증정보 추가
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if(token != null){
            if (jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(()->new CustomException(ErrorCode.EMPTY_CLIENT));
                return user;
            }throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
        else throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }
}

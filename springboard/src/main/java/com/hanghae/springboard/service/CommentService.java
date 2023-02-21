package com.hanghae.springboard.service;

import com.hanghae.springboard.domain.comment.dto.CommentRequestDto;
import com.hanghae.springboard.domain.comment.dto.CommentResponseDto;
import com.hanghae.springboard.domain.comment.entity.Comment;
import com.hanghae.springboard.domain.letter.entity.Letter;
import com.hanghae.springboard.domain.user.entity.User;
import com.hanghae.springboard.dto.StatusResponseDto;
import com.hanghae.springboard.entity.UserRoleEnum;
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

        Letter letter = letterRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_DATA));// 게시글의 저장 유무

        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,letter, user));

        return ResponseEntity.ok(commentRepository.findById(comment.getId()).map(CommentResponseDto::new));
    }
    @Transactional
    public ResponseEntity<?> modifyComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        User user = jwtValidation(request); // user 찾기

        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_DATA)); // comment 찾기

        if (isAuthorized(comment,user)) comment.update(commentRequestDto);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);

        return ResponseEntity.ok(commentRepository.findById(comment.getId()).map(CommentResponseDto::new));
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, HttpServletRequest request) {
        User user = jwtValidation(request); // user 찾기

        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_DATA)); // comment 찾기

        if (isAuthorized(comment, user)) commentRepository.delete(comment);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);

        return ResponseEntity.ok(new StatusResponseDto(true,"success"));
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

    public boolean isAuthorized(Comment comment, User user){
        return (user.getId().equals(comment.getUser().getId())|| user.getRole().equals(UserRoleEnum.ADMIN));
    }
}

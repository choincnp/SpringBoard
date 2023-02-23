package com.hanghae.springboard.service;

import com.hanghae.springboard.domain.comment.dto.CommentRequestDto;
import com.hanghae.springboard.domain.comment.dto.CommentResponseDto;
import com.hanghae.springboard.domain.comment.entity.Comment;
import com.hanghae.springboard.domain.letter.entity.Letter;
import com.hanghae.springboard.domain.like.entity.CommentLike;
import com.hanghae.springboard.domain.user.entity.User;
import com.hanghae.springboard.dto.StatusResponseDto;
import com.hanghae.springboard.entity.UserRoleEnum;
import com.hanghae.springboard.exception.CustomException;
import com.hanghae.springboard.exception.ErrorCode;
import com.hanghae.springboard.jwt.JwtUtil;
import com.hanghae.springboard.repository.CommentLikeRepository;
import com.hanghae.springboard.repository.CommentRepository;
import com.hanghae.springboard.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    private final LetterRepository letterRepository;


    @Transactional
    public ResponseEntity<?> addComment(Long id, CommentRequestDto commentRequestDto, User user) {

        Letter letter = letterRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_DATA));// 게시글의 저장 유무

        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequestDto,letter, user));

        return ResponseEntity.ok(commentRepository.findById(comment.getId()).map(CommentResponseDto::new));
    }
    @Transactional
    public ResponseEntity<?> modifyComment(Long id, CommentRequestDto commentRequestDto, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_DATA)); // comment 찾기

        if (isAuthorized(comment,user)) comment.update(commentRequestDto);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);

        return ResponseEntity.ok(commentRepository.findById(comment.getId()).map(CommentResponseDto::new));
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_DATA)); // comment 찾기

        if (isAuthorized(comment, user)) commentRepository.delete(comment);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);

        return ResponseEntity.ok(new StatusResponseDto<>(true,"success"));
    }

    @Transactional
    public ResponseEntity<?> likeComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(IllegalAccessError::new);
        Optional<CommentLike> found = commentLikeRepository.findByCommentIdAndUserName(id, user.getUsername());
        if (found.isPresent()){
            commentLikeRepository.delete(found.get());
        }else{
            CommentLike commentLike = new CommentLike(user.getUsername(), comment);
            commentLikeRepository.save(commentLike);
        }
        return ResponseEntity.ok(new StatusResponseDto<>(true,"success"));
    }

    public boolean isAuthorized(Comment comment, User user){
        return (user.getId().equals(comment.getUser().getId())|| user.getRole().equals(UserRoleEnum.ADMIN));
    }
}

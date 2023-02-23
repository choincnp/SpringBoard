package com.hanghae.springboard.controller;

import com.hanghae.springboard.domain.comment.dto.CommentRequestDto;
import com.hanghae.springboard.securiry.UserDetailsImpl;
import com.hanghae.springboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.addComment(id,commentRequestDto,userDetails.getUser());
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> modifyComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.modifyComment(id,commentRequestDto,userDetails.getUser());
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }

    @PostMapping("/comment/{id}/like")
    public ResponseEntity<?> likeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.likeComment(id, userDetails.getUser());
    }

}

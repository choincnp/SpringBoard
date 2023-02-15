package com.hanghae.springboard.controller;

import com.hanghae.springboard.dto.CommentRequestDto;
import com.hanghae.springboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public ResponseEntity<?> addComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.addComment(id,commentRequestDto,request);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<?> modifyComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.modifyComment(id,commentRequestDto,request);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id, request);
    }

}

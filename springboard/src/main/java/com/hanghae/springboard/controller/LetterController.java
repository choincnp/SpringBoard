package com.hanghae.springboard.controller;

import com.hanghae.springboard.domain.letter.dto.LetterResponseDto;
import com.hanghae.springboard.domain.letter.dto.LetterRequestDto;
import com.hanghae.springboard.securiry.UserDetailsImpl;
import com.hanghae.springboard.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LetterController {

    private final LetterService letterService;

    @GetMapping("/post")
    public List<LetterResponseDto> viewAll(){
        return letterService.findAll();
    }

    @PostMapping("/post") // 게시글 작성
    public ResponseEntity<?> postLetter(@RequestBody LetterRequestDto letterRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return letterService.postLetter(letterRequestDto,userDetails.getUser());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> viewOne(@PathVariable Long id){
        return letterService.findOne(id);
    }

    @PutMapping("/post/{id}") //PUT METHOD이기 때문에 모든 내용이 다 들어가야 한다.
    public ResponseEntity<?> modifyLetter(@PathVariable Long id, @RequestBody LetterRequestDto letterRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return letterService.modifyLetter(id,letterRequestDto,userDetails.getUser());
    }

    @DeleteMapping("/post/{id}") //RequestBody 방식으로 구현, body : raw - text
    public ResponseEntity<?> deleteLetter(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return letterService.deleteLetter(id, userDetails.getUser());
    }

    @PostMapping("/post/{id}/like")
    public ResponseEntity<?> likeLetter(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return letterService.likeLetter(id, userDetails.getUser());
    }

}

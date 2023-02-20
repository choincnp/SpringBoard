package com.hanghae.springboard.controller;

import com.hanghae.springboard.domain.letter.dto.LetterResponseDto;
import com.hanghae.springboard.domain.letter.dto.LetterRequestDto;
import com.hanghae.springboard.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private final LetterService letterService;

    @GetMapping("/posts")
    public List<LetterResponseDto> viewAll(){
        return letterService.findAll();
    }

    @PostMapping("/post") // 게시글 작성
    public LetterResponseDto postLetter(@RequestBody LetterRequestDto letterRequestDto, HttpServletRequest request){
        return letterService.postLetter(letterRequestDto,request);
    }

    @GetMapping("/post/{id}")
    public LetterResponseDto viewOne(@PathVariable Long id){
        return letterService.findOne(id);
    }

    @PutMapping("/post/{id}") //PUT METHOD이기 때문에 모든 내용이 다 들어가야 한다.
    public ResponseEntity<?> modifyLetter(@PathVariable Long id, @RequestBody LetterRequestDto letterRequestDto, HttpServletRequest request){
        return letterService.modifyLetter(id,letterRequestDto,request);
    }

    @DeleteMapping("/post/{id}") //RequestBody 방식으로 구현, body : raw - text
    public ResponseEntity<?> deleteLetter(@PathVariable Long id, HttpServletRequest request) {
        return letterService.deleteLetter(id, request);
    }

}

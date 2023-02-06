package com.hanghae.springboard.controller;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/post")
    public Long postLetter(LetterRequestDto letterRequestDto){
        return letterService.postLetter(letterRequestDto);
    }

    @GetMapping("/post/{id}")
    public Long viewOne(@PathVariable Long id){
        return letterService.findOne(id).getId();
    }

    @PutMapping("/post/{id}") //PUT METHOD이기 때문에 모든 내용이 다 들어가야 한다.
    public Long modifyLetter(@PathVariable Long id, LetterRequestDto letterRequestDto){
        return letterService.modifyLetter(id,letterRequestDto);
    }
    @DeleteMapping("/post/{id}")
    public Long deleteLetter(@PathVariable Long id, String password){
        return letterService.deleteLetter(id, password);
    }


}

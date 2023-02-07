package com.hanghae.springboard.controller;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.ui.Model;
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
    public Long postLetter(@RequestBody LetterRequestDto letterRequestDto){
        return letterService.postLetter(letterRequestDto);
    }

    @GetMapping("/post/{id}")
    public Long viewOne(@PathVariable Long id){
        return letterService.findOne(id).getId();
    }

    @PutMapping("/post/{id}") //PUT METHOD이기 때문에 모든 내용이 다 들어가야 한다.
    public Long modifyLetter(@PathVariable Long id, @RequestBody LetterRequestDto letterRequestDto){
        return letterService.modifyLetter(id,letterRequestDto);
    }
//    @DeleteMapping("/post/{id}") //ModelAttribute 방식으로 구현, body : x-www.form-urlencoded
//    public String deleteLetter(@PathVariable Long id, @ModelAttribute("password") String password) throws JSONException {
//        return letterService.deleteLetter(id, password);
//    }

//    @DeleteMapping("/post/{id}") //REQUESTPARAM 방식으로 구현, body : form-data
//    public String deleteLetter(@PathVariable Long id, @RequestParam String password, Model m) throws JSONException {
//        m.addAttribute("password",password);
//        return letterService.deleteLetter(id, password);
//    }

    @DeleteMapping("/post/{id}") //RequestBody 방식으로 구현, body : raw - text
    public String deleteLetter(@PathVariable Long id, @RequestBody String password) throws JSONException {
        return letterService.deleteLetter(id, password);
    }

}

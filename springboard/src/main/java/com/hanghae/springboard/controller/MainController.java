package com.hanghae.springboard.controller;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> deleteLetter(@PathVariable Long id, HttpServletRequest request) {
        return letterService.deleteLetter(id, request);
    }

}

package com.hanghae.springboard.service;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.entity.Letter;
import com.hanghae.springboard.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;

    @Transactional
    public Letter postLetter(LetterRequestDto letterRequestDto){
        Letter letter = new Letter(letterRequestDto);
        letterRepository.save(letter);
        return letter;
    }

    @Transactional
    public List<Letter> findAll(){
        return letterRepository.findAllByOrderByModifiedAtDesc();
    }


}

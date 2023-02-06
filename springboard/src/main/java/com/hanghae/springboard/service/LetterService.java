package com.hanghae.springboard.service;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.entity.Letter;
import com.hanghae.springboard.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;

    @Transactional
    public long postLetter(LetterRequestDto letterRequestDto){
        Letter letter = new Letter(letterRequestDto);
        letterRepository.save(letter);
        return new LetterResponseDto(letter).getId();
    }

    @Transactional(readOnly = true)
    public List<LetterResponseDto> findAll(){ // service Layer에서 Entity >> DTO 변환작업, 사유 : LazyInitializationException 위험부담 줄임
        return letterRepository.findAllByOrderByModifiedAtDesc().stream().map(LetterResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LetterResponseDto findOne(Long id){
        return letterRepository.findById(id).map(LetterResponseDto::new).orElseThrow(() -> new IllegalArgumentException("아이디가 없어요"));
    }

}

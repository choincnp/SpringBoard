package com.hanghae.springboard.service;

import com.hanghae.springboard.dto.LetterResponseDto;
import com.hanghae.springboard.dto.LetterRequestDto;
import com.hanghae.springboard.entity.Letter;
import com.hanghae.springboard.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
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
        return letterRepository.findById(id).map(LetterResponseDto::new).orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
    }
    @Transactional
    public Long modifyLetter(Long id, LetterRequestDto letterRequestDto) throws IllegalStateException {
        Letter letter = letterRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("아이디 없음"));
        if (!letter.isValid(letterRequestDto)) throw new RuntimeException("비밀번호 불일치");
        letter.update(letterRequestDto);
        return letter.getId();
    }

    public String deleteLetter(Long id, String password) throws JSONException {
        Letter letter = letterRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("아이디 없음"));
        if(!letter.isValid(password)) throw new RuntimeException("비밀번호 불일치");
        letterRepository.delete(letter);
        JSONObject success = new JSONObject();
        success.put("success", true);
        return success.toString();
    }
}

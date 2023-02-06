package com.hanghae.springboard.dto;

import com.hanghae.springboard.entity.Letter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class LetterResponseDto {
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long id;
    private String title;
    private String username;


}
//{
//"createdAt": "2022-07-25T12:43:01.226062”,
//"modifiedAt": "2022-07-25T12:43:01.226062”,
//"id": 1,
//"title": "title2",
//"content": "content2",
//"author": "author2"
//},
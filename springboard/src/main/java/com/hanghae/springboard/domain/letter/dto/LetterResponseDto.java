package com.hanghae.springboard.domain.letter.dto;

import com.hanghae.springboard.domain.letter.entity.Letter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class LetterResponseDto {
    private Long id;

    private String title;
    private String contents;
    private String username;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public LetterResponseDto(Letter letter){
        this.title = letter.getTitle();
        this.createAt = letter.getCreatedAt();
        this.modifiedAt = letter.getModifiedAt();
        this.id = letter.getId();
        this.contents = letter.getContents();
        this.username = letter.getUser().getUsername();
    }

}
//{
//"createdAt": "2022-07-25T12:43:01.226062”,
//"modifiedAt": "2022-07-25T12:43:01.226062”,
//"id": 1,
//"title": "title2",
//"content": "content2",
//"author": "author2"
//},
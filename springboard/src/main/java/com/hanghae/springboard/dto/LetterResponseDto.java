package com.hanghae.springboard.dto;

import com.hanghae.springboard.entity.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class LetterResponseDto {
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long id;
    private String contents;
    private String username;

    public LetterResponseDto(Letter letter){
        this.createAt = letter.getCreatedAt();
        this.modifiedAt = letter.getModifiedAt();
        this.id = letter.getId();
        this.contents = letter.getContents();
        this.username = letter.getUsername();
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
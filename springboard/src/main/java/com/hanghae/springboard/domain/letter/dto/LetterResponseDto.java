package com.hanghae.springboard.domain.letter.dto;

import com.hanghae.springboard.domain.comment.dto.CommentResponseDto;
import com.hanghae.springboard.domain.comment.entity.Comment;
import com.hanghae.springboard.domain.letter.entity.Letter;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LetterResponseDto {
    private Long id;

    private String title;
    private String contents;
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> comments = new ArrayList<>();

    public LetterResponseDto(Letter letter){
        this.title = letter.getTitle();
        this.createdAt = letter.getCreatedAt();
        this.modifiedAt = letter.getModifiedAt();
        this.id = letter.getId();
        this.contents = letter.getContents();
        this.username = letter.getUser().getUsername();
        this.comments = letter.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
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
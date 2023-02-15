package com.hanghae.springboard.entity;

import com.hanghae.springboard.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LETTER_ID",nullable = false)
    private Letter Letter;

    @ManyToOne
    @JoinColumn(name="USER_ID", nullable = false)
    private User User;

    private String contents;

    public Comment(CommentRequestDto commentRequestDto, Letter letter, User user) {
        this.contents = commentRequestDto.getContents();
        this.Letter = letter;
        this.User = user;
    }

    public void update(CommentRequestDto commentRequestDto){
        this.contents = commentRequestDto.getContents();
    }
}

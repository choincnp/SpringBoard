package com.hanghae.springboard.domain.comment.entity;

import com.hanghae.springboard.domain.comment.dto.CommentRequestDto;
import com.hanghae.springboard.domain.letter.entity.Letter;
import com.hanghae.springboard.entity.Timestamped;
import com.hanghae.springboard.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LETTER_ID",nullable = false)
    private com.hanghae.springboard.domain.letter.entity.Letter Letter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private com.hanghae.springboard.domain.user.entity.User User;

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

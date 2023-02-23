package com.hanghae.springboard.domain.comment.entity;

import com.hanghae.springboard.domain.comment.dto.CommentRequestDto;
import com.hanghae.springboard.domain.letter.entity.Letter;
import com.hanghae.springboard.domain.like.entity.CommentLike;
import com.hanghae.springboard.entity.Timestamped;
import com.hanghae.springboard.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LETTER_ID",nullable = false)
    private Letter letter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private  User User;

    private String contents;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> likes = new ArrayList<>();

    public Comment(CommentRequestDto commentRequestDto, Letter letter, User user) {
        this.contents = commentRequestDto.getContents();
        this.letter = letter;
        this.User = user;
    }

    public void update(CommentRequestDto commentRequestDto){
        this.contents = commentRequestDto.getContents();
    }
}

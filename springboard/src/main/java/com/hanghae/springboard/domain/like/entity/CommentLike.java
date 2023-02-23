package com.hanghae.springboard.domain.like.entity;

import com.hanghae.springboard.domain.comment.entity.Comment;
import com.hanghae.springboard.domain.letter.entity.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    public CommentLike(String userName, Comment comment){
        this.userName = userName;
        this.comment = comment;
    }
}

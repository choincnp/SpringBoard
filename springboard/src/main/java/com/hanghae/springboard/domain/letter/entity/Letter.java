package com.hanghae.springboard.domain.letter.entity;

import com.hanghae.springboard.domain.comment.entity.Comment;
import com.hanghae.springboard.domain.letter.dto.LetterRequestDto;
import com.hanghae.springboard.domain.user.entity.User;
import com.hanghae.springboard.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Letter extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private User user; // 작성자명

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents; // 내용

    @OneToMany(mappedBy = "Letter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public Letter(LetterRequestDto letterRequestDto, User user){
        this.user = user;
        this.title = letterRequestDto.getTitle();
        this.contents = letterRequestDto.getContents();
    }

    public void update(LetterRequestDto letterRequestDto){
        this.contents = letterRequestDto.getContents();
        this.title = letterRequestDto.getTitle();
    }

}

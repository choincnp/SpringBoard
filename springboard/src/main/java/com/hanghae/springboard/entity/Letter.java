package com.hanghae.springboard.entity;

import com.hanghae.springboard.dto.LetterRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Letter extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시물 번호

    @Column(nullable = false)
    private String username; // 작성자명

    @Column(nullable = false)
    private String contents; // 내용

    @Column(nullable = false)
    private String password;

    public Letter(Long id, String username, String contents, String password) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.password = password;
    }

    public Letter(LetterRequestDto letterRequestDto){
        this.id = letterRequestDto.getId();
        this.username = letterRequestDto.getUsername();
        this.contents = letterRequestDto.getContents();
        this.password = letterRequestDto.getPassword();
    }

}

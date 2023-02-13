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
    private String title;

    @Column(nullable = false)
    private String contents; // 내용

    @Column(nullable = false)
    private String password;

    public Letter(LetterRequestDto letterRequestDto, String userName){
        this.username = userName;
        this.title = letterRequestDto.getTitle();
        this.contents = letterRequestDto.getContents();
        this.password = letterRequestDto.getPassword();
    }

    public boolean isValid(LetterRequestDto letterRequestDto){
        return password.equals(letterRequestDto.getPassword());
    }

    public boolean isValid(String password){
        return this.password.equals(password);
    }

    public void update(LetterRequestDto letterRequestDto){
        this.username = letterRequestDto.getUsername();
        this.contents = letterRequestDto.getContents();
        this.title = letterRequestDto.getTitle();
    }
}

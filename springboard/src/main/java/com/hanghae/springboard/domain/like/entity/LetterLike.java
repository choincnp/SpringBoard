package com.hanghae.springboard.domain.like.entity;

import com.hanghae.springboard.domain.letter.entity.Letter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class LetterLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LETTER_ID")
    private Letter letter;

    public LetterLike(String userName, Letter letter){
        this.userName = userName;
        this.letter = letter;
    }


}

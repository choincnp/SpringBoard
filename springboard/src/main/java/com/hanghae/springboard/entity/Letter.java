package com.hanghae.springboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Letter extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    public Letter(Long id, String username, String contents, String password) {
        this.id = id;
        this.username = username;
        this.contents = contents;
        this.password = password;
    }

}

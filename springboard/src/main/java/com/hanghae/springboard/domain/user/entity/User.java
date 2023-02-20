package com.hanghae.springboard.domain.user.entity;

import com.hanghae.springboard.domain.user.dto.SignupRequestDto;
import com.hanghae.springboard.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(SignupRequestDto signupRequestDto) { // 추후 builderPattern적용예정
        this.username = signupRequestDto.getUsername();
        this.password = signupRequestDto.getPassword();
        if (signupRequestDto.isAdmin()) this.role = UserRoleEnum.ADMIN;
        else this.role = UserRoleEnum.USER;
    }

}

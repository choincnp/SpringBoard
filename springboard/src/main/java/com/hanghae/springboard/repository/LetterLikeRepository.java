package com.hanghae.springboard.repository;

import com.hanghae.springboard.domain.like.entity.LetterLike;
import com.hanghae.springboard.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LetterLikeRepository extends JpaRepository<LetterLike, Long> {

    Optional<LetterLike> findByLetterIdAndUserName(Long id, String username);

}

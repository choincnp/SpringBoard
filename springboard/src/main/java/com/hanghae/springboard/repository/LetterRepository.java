package com.hanghae.springboard.repository;

import com.hanghae.springboard.domain.letter.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    List<Letter> findAllByOrderByModifiedAtDesc();
}

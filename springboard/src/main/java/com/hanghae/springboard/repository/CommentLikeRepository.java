package com.hanghae.springboard.repository;

import com.hanghae.springboard.domain.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {

    Optional<CommentLike> findByCommentIdAndUserName(Long id, String username);
}

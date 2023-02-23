package com.hanghae.springboard.service;

import com.hanghae.springboard.domain.comment.dto.CommentResponseDto;
import com.hanghae.springboard.domain.letter.dto.LetterResponseDto;
import com.hanghae.springboard.domain.letter.dto.LetterRequestDto;
import com.hanghae.springboard.domain.letter.entity.Letter;
import com.hanghae.springboard.domain.like.entity.LetterLike;
import com.hanghae.springboard.domain.user.entity.User;
import com.hanghae.springboard.dto.StatusResponseDto;
import com.hanghae.springboard.entity.UserRoleEnum;
import com.hanghae.springboard.exception.CustomException;
import com.hanghae.springboard.exception.ErrorCode;
import com.hanghae.springboard.repository.LetterLikeRepository;
import com.hanghae.springboard.repository.LetterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LetterService {
    private final LetterRepository letterRepository;

    private final LetterLikeRepository letterLikeRepository;

    @Transactional
    public ResponseEntity<?> postLetter(LetterRequestDto letterRequestDto, User user){
        Letter letter = letterRepository.saveAndFlush(new Letter(letterRequestDto,user));

        return ResponseEntity.ok(
                letterRepository.findById(letter.getId()).map(LetterResponseDto::new).orElseThrow(
                ()->new IllegalArgumentException("게시글이 존재하지 않습니다."))
        );
    }

    @Transactional(readOnly = true)
    public List<LetterResponseDto> findAll(){ // service Layer에서 Entity >> DTO 변환작업, 사유 : LazyInitializationException 위험부담 줄임
        return letterRepository.findAllByOrderByModifiedAtDesc().stream().map(LetterResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findOne(Long id){

        Letter letter = letterRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글 없음")
        );

        List<CommentResponseDto> collect = letter.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(LetterResponseDto.builder() // 이런 단순한 로직에서는 builder패턴이 번거로울 수 있다.
                    .id(letter.getId())
                    .createdAt(letter.getCreatedAt())
                    .modifiedAt(letter.getModifiedAt())
                    .title(letter.getTitle())
                    .username(letter.getUser().getUsername())
                    .contents(letter.getContents())
                    .comments(collect)
                    .likeCount((long) letter.getLikes().size())
                    .build()
        );
    }
    @Transactional
    public ResponseEntity<?> modifyLetter(Long id, LetterRequestDto letterRequestDto, User user) {
        Letter letter = letterRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if (user.getUsername().equals(letter.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN))letter.update(letterRequestDto);
        else throw new CustomException(ErrorCode.EMPTY_CLIENT);

        return ResponseEntity.ok(letterRepository.findById(id).map(LetterResponseDto::new));
    }
    @Transactional
    public ResponseEntity<?> deleteLetter(Long id, User user) {
        Letter letter = letterRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (user.getUsername().equals(letter.getUser().getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) letterRepository.delete(letter);

        return ResponseEntity.ok("게시글 삭제 성공");
    }
    @Transactional
    public ResponseEntity<?> likeLetter(Long id, User user) {
        Letter letter = letterRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Optional<LetterLike> found = letterLikeRepository.findByLetterIdAndUserName(id, user.getUsername());
        if (found.isPresent()){
            letterLikeRepository.delete(found.get());
        } else{
            LetterLike like = new LetterLike(user.getUsername(), letter);
            letterLikeRepository.save(like);
        }
        return ResponseEntity.ok(new StatusResponseDto<>(true, "success"));
    }
}

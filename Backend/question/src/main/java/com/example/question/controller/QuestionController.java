package com.example.question.controller;

import com.example.question.dto.CreateQuestionRequest;
import com.example.question.dto.QuestionResponse;
import com.example.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController{
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
        @Valid @RequestBody CreateQuestionRequest request,
        @AuthenticationPrincipal Jwt jwt
    ){
        String authorEmail = jwt.getSubject();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(questionService.createQuestion(request, authorEmail));
    }

    @GetMapping
    public ResponseEntity<Page<QuestionResponse>> getWall(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(questionService.getWallQuestions(page, size));
    }

    @PatchMapping("/{id}/upvote")
    public ResponseEntity<QuestionResponse> upvote(@PathVariable Long id){
        return ResponseEntity.ok(questionService.upvoteQuestion(id));
    }
}
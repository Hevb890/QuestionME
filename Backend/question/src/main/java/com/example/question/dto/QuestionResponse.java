package com.company.questionservice.question.dto;

import java.time.LocalDateTime;

public record QuestionResponse(
    Long id, 
    String title,
    String content,
    String authorEmail,
    int upvotes,
    LocalDateTime createdAt
){}
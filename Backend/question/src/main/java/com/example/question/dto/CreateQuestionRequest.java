package com.example.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateQuestionRequest (
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must be under 255 characters")
    String title,

    @NotBlank(message = "Content cannot be blank")
    String content
){}

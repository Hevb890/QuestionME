package com.company.questionservice.question.service;

import com.company.questionservice.question.dto.CreateQuestionRequest;
import com.company.questionservice.question.dto.QuestionResponse;
import com.company.questionservice.question.entity.Questions;
import com.company.questionservice.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionResponse createQuestion(CreateQuestionRequest request, String authorEmail){
        Questions question = Questions.builder()
            .title(request.title())
            .content(request.content())
            .authorEmail(authorEmail)
            .build();
        
        Questions savedQuestion = questionRepository.save(question);
        return mapToResponse(savedQuestion);
    }

    @Transactional(readOnly = true)
    public Page<QuestionResponse> getWallQuestions(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return questionRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToResponse);
    }

    @Transactional
    public QuestionResponse upvoteQuestion(Long id){
        Questions question = questionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Question not found with ID: " + id));

        question.setUpvotes(question.getUpvotes()+1);
        return mapToResponse(questionRepository.save(question));
    }

    private QuestionResponse mapToResponse(Questions q){
        return new QuestionResponse(
            q.getId(),
            q.getTitle(),
            q.getContent(),
            q.getAuthorEmail(),
            q.getUpvotes(),
            q.getCreatedAt()
        );
    }
}

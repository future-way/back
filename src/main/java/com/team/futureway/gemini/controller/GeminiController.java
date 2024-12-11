package com.team.futureway.gemini.controller;

import com.team.futureway.gemini.dto.QuestionDTO;
import com.team.futureway.gemini.response.AnswerRequest;
import com.team.futureway.gemini.response.GeminiQuestionResponse;
import com.team.futureway.gemini.response.QuestionRequest;
import com.team.futureway.gemini.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<GeminiQuestionResponse> questionMessage(@RequestBody QuestionRequest request) {
        QuestionDTO result = questionService.getQuestionMessage(request.userId());
        return ResponseEntity.ok(GeminiQuestionResponse.of(result));
    }


    @PostMapping("/answer")
    public ResponseEntity<GeminiQuestionResponse> newQuestionMessage(@RequestBody AnswerRequest request) {
        QuestionDTO questionDTO = QuestionDTO.of(
                request.aiConsultationHistoryId(),
                request.userId(),
                request.questionNumber(),
                null,
                request.answer()
        );
        QuestionDTO result = questionService.getNewQuestionMessage(questionDTO);
        return ResponseEntity.ok(GeminiQuestionResponse.of(result));
    }

}

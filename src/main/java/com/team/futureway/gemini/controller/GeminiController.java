package com.team.futureway.gemini.controller;

import com.team.futureway.gemini.dto.AiConsultationSummaryHistoryDTO;
import com.team.futureway.gemini.dto.QuestionDTO;
import com.team.futureway.gemini.response.*;
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

    @PostMapping("/summary")
    public ResponseEntity<SummaryResponse> summary(@RequestBody SummaryRequest request) {
        AiConsultationSummaryHistoryDTO result = questionService.getSummary(request.userId());
        return ResponseEntity.ok(SummaryResponse.of(result));
    }
}

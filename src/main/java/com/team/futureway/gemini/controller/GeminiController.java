package com.team.futureway.gemini.controller;

import com.team.futureway.gemini.dto.AiConsultationSummaryHistoryDTO;
import com.team.futureway.gemini.dto.QuestionDTO;
import com.team.futureway.gemini.dto.UserTypeDTO;
import com.team.futureway.gemini.response.*;
import com.team.futureway.gemini.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Gemini", description = "Gemini API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final QuestionService questionService;

    @Operation(summary = "사용자와 상담 시작 시")
    @PostMapping
    public ResponseEntity<GeminiQuestionResponse> questionMessage(@RequestBody QuestionRequest request) {
        QuestionDTO result = questionService.getQuestionMessage(request.userId());
        return ResponseEntity.ok(GeminiQuestionResponse.of(result));
    }

    @Operation(summary = "상담 진행 API")
    @PostMapping("/answer")
    public ResponseEntity<GeminiQuestionResponse> newQuestionMessage(@RequestBody AnswerRequest request) {
        QuestionDTO questionDTO = QuestionDTO.of(
                request.aiConsultationHistoryId(),
                request.userId(),
                0,
                null,
                request.answer(),
                null
        );
        QuestionDTO result = questionService.getNewQuestionMessage(questionDTO);
        return ResponseEntity.ok(GeminiQuestionResponse.of(result));
    }

    @Operation(summary = "상담 종료 시 (상담 내용 요약)")
    @PostMapping("/summary")
    public ResponseEntity<SummaryResponse> summary(@RequestBody SummaryRequest request) {
        AiConsultationSummaryHistoryDTO result = questionService.getSummary(request.userId());
        return ResponseEntity.ok(SummaryResponse.of(result));
    }

    @Operation(summary = "사용자 유형 저장")
    @PostMapping("/type")
    public ResponseEntity<UserTypeResponse> type(@RequestBody UserTypeRequest request) {
        UserTypeDTO userTypeDTO = UserTypeDTO.of(
                request.userId(),
                request.question(),
                request.selectType(),
                request.answer(),
                request.userType()
        );
        UserTypeDTO result = questionService.saveUserType(userTypeDTO);
        return ResponseEntity.ok(UserTypeResponse.of(result));
    }
}

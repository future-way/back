package com.team.futureway.consult.controller;

import com.team.futureway.consult.dto.SummaryDTO;
import com.team.futureway.consult.dto.QuestionDTO;
import com.team.futureway.consult.response.*;
import com.team.futureway.consult.service.CreateAndReturnSummaryUseCase;
import com.team.futureway.consult.service.CreateFirstQuestionUseCase;
import com.team.futureway.consult.service.CreateNextQuestionFromAnswerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gemini", description = "Gemini API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gemini")
public class ConsultController {

    private final CreateFirstQuestionUseCase createFirstQuestionUseCase;

    private final CreateNextQuestionFromAnswerUseCase createQuestionWithAnswerUseCase;

    private final CreateAndReturnSummaryUseCase createAndReturnSummaryUseCase;

    @Operation(summary = "첫 질문 생성")
    @PostMapping
    public ResponseEntity<QuestionResponse> firstQuestion(@RequestBody QuestionRequest request) {
        QuestionDTO result = createFirstQuestionUseCase.execute(request.userId());
        return ResponseEntity.ok(QuestionResponse.of(result));
    }

    @Operation(summary = "답변 저장 및 질문 생성")
    @PostMapping("/answer")
    public ResponseEntity<QuestionResponse> question(@RequestBody AnswerRequest request) {
        QuestionDTO questionDTO = QuestionDTO.of(
                request.aiConsultationHistoryId(),
                request.userId(),
                0,
                null,
                request.answer(),
                null
        );
        QuestionDTO result = createQuestionWithAnswerUseCase.execute(questionDTO);
        return ResponseEntity.ok(QuestionResponse.of(result));
    }

    @Operation(summary = "상담 내용 요약")
    @PostMapping("/summary")
    public ResponseEntity<SummaryResponse> summary(@RequestBody SummaryRequest request) {
        SummaryDTO result = createAndReturnSummaryUseCase.execute(request.userId());
        return ResponseEntity.ok(SummaryResponse.of(result));
    }

}

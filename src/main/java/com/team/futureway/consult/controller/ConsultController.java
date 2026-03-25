package com.team.futureway.consult.controller;

import com.team.futureway.consult.dto.SummaryDTO;
import com.team.futureway.consult.dto.QuestionDTO;
import com.team.futureway.consult.request.AnswerRequest;
import com.team.futureway.consult.response.QuestionResponse;
import com.team.futureway.consult.response.SummaryResponse;
import com.team.futureway.consult.service.CreateAndReturnSummaryUseCase;
import com.team.futureway.consult.service.CreateFirstQuestionUseCase;
import com.team.futureway.consult.service.CreateNextQuestionFromAnswerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Consult", description = "진로 상담 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/consults")
public class ConsultController {

    private final CreateFirstQuestionUseCase createFirstQuestionUseCase;

    private final CreateNextQuestionFromAnswerUseCase createQuestionWithAnswerUseCase;

    private final CreateAndReturnSummaryUseCase createAndReturnSummaryUseCase;

    @Operation(summary = "첫 질문 생성")
    @PostMapping("/users/{userId}")
    public ResponseEntity<QuestionResponse> firstQuestion(@PathVariable Long userId) {
        QuestionDTO result = createFirstQuestionUseCase.execute(userId);
        return ResponseEntity.ok(QuestionResponse.of(result));
    }

    @Operation(summary = "답변 저장 및 질문 생성")
    @PostMapping("/{questionId}/answers")
    public ResponseEntity<QuestionResponse> question(@PathVariable Long questionId, @RequestBody AnswerRequest request) {
        QuestionDTO questionDTO = QuestionDTO.of(
                questionId,
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
    @PostMapping("/users/{userId}/summary")
    public ResponseEntity<SummaryResponse> summary(@PathVariable Long userId) {
        SummaryDTO result = createAndReturnSummaryUseCase.execute(userId);
        return ResponseEntity.ok(SummaryResponse.of(result));
    }

}

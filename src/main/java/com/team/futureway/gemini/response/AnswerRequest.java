package com.team.futureway.gemini.response;

import com.team.futureway.gemini.dto.QuestionDTO;

public record AnswerRequest(Long aiConsultationHistoryId, Long userId, int questionNumber, String answer) {

    public static AnswerRequest of(QuestionDTO dto) {
        return new AnswerRequest(dto.getAiConsultationHistoryId(), dto.getUserId(), dto.getQuestionNumber(), dto.getAnswer());
    }
}

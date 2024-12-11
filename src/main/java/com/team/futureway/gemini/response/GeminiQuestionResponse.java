package com.team.futureway.gemini.response;

import com.team.futureway.gemini.dto.QuestionDTO;

public record GeminiQuestionResponse(Long aiConsultationHistoryId, Long userId, int questionNumber, String questionMessage, String answer) {

    public static GeminiQuestionResponse of(QuestionDTO dto) {
        return new GeminiQuestionResponse(
                dto.getAiConsultationHistoryId()
                , dto.getUserId()
                , dto.getQuestionNumber()
                , dto.getQuestionMessage()
                , dto.getAnswer()
        );
    }
}

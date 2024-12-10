package com.team.futureway.gemini.response;

import com.team.futureway.gemini.dto.GeminiQuestionDTO;

public record GeminiQuestionResponse(Long aiConsultationHistoryId,Long userId, int questionNumber, String questionMessage, String answer) {

    public static GeminiQuestionResponse of(GeminiQuestionDTO dto) {
        return new GeminiQuestionResponse(
                dto.getAiConsultationHistoryId()
                , dto.getUserId()
                , dto.getQuestionNumber()
                , dto.getQuestionMessage()
                , dto.getAnswer()
        );
    }
}

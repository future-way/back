package com.team.futureway.consult.response;

import com.team.futureway.consult.dto.QuestionDTO;

public record QuestionResponse(Long aiConsultationHistoryId, Long userId, int questionNumber, String questionMessage, String answer) {

    public static QuestionResponse of(QuestionDTO dto) {
        return new QuestionResponse(
                dto.getQuestionId()
                , dto.getUserId()
                , dto.getQuestionNumber()
                , dto.getQuestionMessage()
                , dto.getAnswer()
        );
    }
}

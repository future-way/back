package com.team.futureway.gemini.response;

public record AnswerRequest(Long aiConsultationHistoryId, Long userId, int questionNumber, String answer) {
}

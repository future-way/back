package com.team.futureway.gemini.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Slf4j
public class GeminiQuestionDTO {

    private Long aiConsultationHistoryId; // AI 상담 기록 ID
    private Long userId; // 사용자 ID
    private int questionNumber; // 질문 번호
    private String questionMessage; // 질문 메시지
    private String answer; // 답변

    public static GeminiQuestionDTO of(
            Long aiConsultationHistoryId
            , Long userId
            , int questionNumber
            , String questionMessage
            , String answer) {
        return new GeminiQuestionDTO(
                aiConsultationHistoryId
                , userId
                , questionNumber
                , questionMessage
                , answer);
    }
}

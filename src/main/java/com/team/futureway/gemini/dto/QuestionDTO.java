package com.team.futureway.gemini.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Slf4j
public class QuestionDTO {

    private Long aiConsultationHistoryId; // AI 상담 기록 ID
    private Long userId; // 사용자 ID
    private int questionNumber; // 질문 번호
    private String questionMessage; // 질문 메시지
    private String answer; // 답변
    private LocalDateTime createdDate; // 생성 일자

    public static QuestionDTO of(Long aiConsultationHistoryId, Long userId, int questionNumber, String questionMessage, String answer, LocalDateTime createdDate) {
        return new QuestionDTO(aiConsultationHistoryId, userId, questionNumber, questionMessage, answer, createdDate);
    }
}

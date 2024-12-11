package com.team.futureway.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AiConsultationSummaryHistoryDTO {

    private Long userId; // 사용자 아이디
    private String summary; // 요약 내용
    private LocalDateTime createdDate; // 생성 일자

    public static AiConsultationSummaryHistoryDTO of(Long userId, String summary, LocalDateTime createdDate) {
        return new AiConsultationSummaryHistoryDTO(userId, summary, createdDate);
    }
}
package com.team.futureway.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AiConsultationSummaryHistoryDTO {

    private Long userId; // 사용자 아이디
    private String summary; // 요약 내용
    private LocalDateTime createdDate; // 생성 일자
    private List<String> hollandTypes;

    public static AiConsultationSummaryHistoryDTO of(Long userId, String summary, LocalDateTime createdDate, List<String> hollandTypes) {
        return new AiConsultationSummaryHistoryDTO(userId, summary, createdDate, hollandTypes);
    }
}
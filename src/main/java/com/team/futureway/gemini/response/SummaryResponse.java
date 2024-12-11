package com.team.futureway.gemini.response;

import com.team.futureway.gemini.dto.AiConsultationSummaryHistoryDTO;

import java.time.LocalDateTime;

public record SummaryResponse(
        Long userId,           // 사용자 아이디
        String summary,        // 요약 내용
        LocalDateTime createdDate // 생성 일자
) {
    public static SummaryResponse of(AiConsultationSummaryHistoryDTO dto) {
        return new SummaryResponse(dto.getUserId(), dto.getSummary(), dto.getCreatedDate());
    }
}
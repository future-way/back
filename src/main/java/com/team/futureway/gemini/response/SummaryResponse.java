package com.team.futureway.gemini.response;

import com.team.futureway.gemini.dto.AiConsultationSummaryHistoryDTO;

import java.time.LocalDateTime;
import java.util.List;

public record SummaryResponse(Long userId, String summary, LocalDateTime createdDate, List<String> hollandTypes) {
    public static SummaryResponse of(AiConsultationSummaryHistoryDTO dto) {
        return new SummaryResponse(dto.getUserId(), dto.getSummary(), dto.getCreatedDate(), dto.getHollandTypes());
    }
}
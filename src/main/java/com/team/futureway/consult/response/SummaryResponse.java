package com.team.futureway.consult.response;

import com.team.futureway.consult.dto.SummaryDTO;

import java.time.LocalDateTime;
import java.util.List;

public record SummaryResponse(Long userId, String summary, String recommend, String userType, LocalDateTime createdDate, List<String> hollandTypes) {
    public static SummaryResponse of(SummaryDTO dto) {
        return new SummaryResponse(dto.getUserId(), dto.getSummary(), dto.getRecommend(), dto.getUserType(), dto.getCreatedDate(), dto.getHollandTypes());
    }
}
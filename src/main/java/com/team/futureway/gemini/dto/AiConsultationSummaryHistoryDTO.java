package com.team.futureway.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class AiConsultationSummaryHistoryDTO {

    private Long userId;
    private String summary;
    private String recommend;
    private String userType;
    private LocalDateTime createdDate;
    private List<String> hollandTypes;

    public static AiConsultationSummaryHistoryDTO of(Long userId, String summary, String recommend, String userType,  LocalDateTime createdDate, List<String> hollandTypes) {
        return new AiConsultationSummaryHistoryDTO(userId, summary, recommend, userType, createdDate, hollandTypes);
    }
}
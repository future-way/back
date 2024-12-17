package com.team.futureway.consult.dto;

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
public class SummaryDTO {

    private Long userId;
    private String summary;
    private String recommend;
    private String userType;
    private LocalDateTime createdDate;
    private List<String> hollandTypes;

    public static SummaryDTO of(Long userId, String summary, String recommend, String userType, LocalDateTime createdDate, List<String> hollandTypes) {
        return new SummaryDTO(userId, summary, recommend, userType, createdDate, hollandTypes);
    }
}
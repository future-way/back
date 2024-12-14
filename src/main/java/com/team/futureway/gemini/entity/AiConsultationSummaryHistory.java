package com.team.futureway.gemini.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "ai_consultation_summary_history")
public class AiConsultationSummaryHistory {
    @Id
    @Column(name = "ai_consultation_summary_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiConsultationSummaryHistoryId;

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 아이디

    @Column(name = "summary")
    private String summary; // 요약 내용

    @Column(name = "recommend")
    private String recommend; // 진로 추천 내용

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate; // 생성 일자


    public static AiConsultationSummaryHistory of(Long aiConsultationSummaryHistoryId, Long userId, String summary, String recommend) {
        return new AiConsultationSummaryHistory(aiConsultationSummaryHistoryId, userId, summary, recommend, LocalDateTime.now());
    }
}

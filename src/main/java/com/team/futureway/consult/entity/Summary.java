package com.team.futureway.consult.entity;

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
@Table(name = "summary")
public class Summary {
    @Id
    @Column(name = "summary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summaryId;

    @Column(name = "user_id", nullable = false)
    private Long userId; // 사용자 아이디

    @Column(name = "summary")
    private String summary; // 요약 내용

    @Column(name = "recommend")
    private String recommend; // 진로 추천 내용

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate; // 생성 일자


    public static Summary of(Long aiConsultationSummaryHistoryId, Long userId, String summary, String recommend) {
        return new Summary(aiConsultationSummaryHistoryId, userId, summary, recommend, LocalDateTime.now());
    }

    public void removeBracesAndTextFromQuestion(String createdSummary) {
        if (createdSummary != null) {
            this.summary = createdSummary.replaceAll("\\{.*?\\}", "").trim();
        }
    }

}

package com.team.futureway.gemini.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "ai_consultation_history")
public class AiConsultationHistory {
    @Id
    @Column(name = "ai_consultation_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aiConsultationHistoryId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question_number", nullable = false)
    private int questionNumber;

    @Column(name = "question_message", nullable = false, length = 255)
    private String questionMessage;


    @Column(name = "answer", length = 255)
    private String answer;

    public static AiConsultationHistory of(Long aiConsultationHistoryId, Long userId, int questionNumber, String questionMessage, String answer) {
        return new AiConsultationHistory(aiConsultationHistoryId, userId, questionNumber, questionMessage, answer);
    }

    // 질문 번호 증가 로직
    public void incrementQuestionNumber() {
        this.questionNumber++;
    }

}

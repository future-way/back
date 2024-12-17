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
@Table(name = "question")
public class Question {
    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question_number", nullable = false)
    private int questionNumber;

    @Column(name = "question_message", nullable = false, length = 255)
    private String questionMessage;

    @Column(name = "answer", length = 255)
    private String answer;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate; // 생성 일자

    public static Question of(Long aiConsultationHistoryId, Long userId, int questionNumber, String questionMessage, String answer) {
        return new Question(aiConsultationHistoryId, userId, questionNumber, questionMessage, answer, LocalDateTime.now());
    }

    public void incrementQuestionNumber() {
        this.questionNumber++;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

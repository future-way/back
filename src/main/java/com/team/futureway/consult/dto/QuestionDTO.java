package com.team.futureway.consult.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Slf4j
public class QuestionDTO {

    private Long questionId;
    private Long userId;
    private int questionNumber;
    private String questionMessage;
    private String answer;
    private LocalDateTime createdDate;

    public static QuestionDTO of(Long questionId, Long userId, int questionNumber, String questionMessage, String answer, LocalDateTime createdDate) {
        return new QuestionDTO(questionId, userId, questionNumber, questionMessage, answer, createdDate);
    }
}

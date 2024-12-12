package com.team.futureway.prompt.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Table(name = "user")
public class Answer {

  @Id
  @Column(name = "answer_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long answerId;

  @Column(name = "answer", length = 50)
  private String answer;

  @Column(name = "consult_id")
  private Long consultId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "answer_at", nullable = false)
  private LocalDateTime answerAt;

  public static Answer of(Long answerId, String answer, Long consultId, Long userId, LocalDateTime answerAt) {
    return new Answer(answerId, answer, consultId, userId, answerAt);
  }

}

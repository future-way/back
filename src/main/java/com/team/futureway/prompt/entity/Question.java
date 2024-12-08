package com.team.futureway.prompt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {

  @Id
  @Column(name = "question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long questionId;

  @Column(name = "consult_id", nullable = false)
  private Long consultId;

  @Column(name = "question", length = 100)
  private String question;

  @Column(name = "question_at")
  @CreatedDate
  private LocalDateTime questionAt;

}

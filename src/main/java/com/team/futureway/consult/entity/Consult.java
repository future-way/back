package com.team.futureway.consult.entity;

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
@Table(name = "consult")
public class Consult {

  @Id
  @Column(name = "consult_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long consultId;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "title", length = 50)
  private String title;

  @Column(name = "kind", length = 10)
  private String kind;

  @Column(name = "consult_at")
  @CreatedDate
  private LocalDateTime consultAt;

}

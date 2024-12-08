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
@Table(name = "summation")
public class Summation {

  @Id
  @Column(name = "summation_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long summationId;

  @Column(name = "consult_id", nullable = false)
  private Long consultId;

  @Column(name = "summury", length = 300)
  private String summury;

  @Column(name = "summation_at")
  @CreatedDate
  private LocalDateTime summationAt;

}

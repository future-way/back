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
public class ConsultDTO {
  private Long consultId;
  private Long userId;
  private String title;
  private String kind;
  private String interest;
  private LocalDateTime consultAt;

  public static ConsultDTO of(Long consultId, Long userId, String title, String kind, String interest, LocalDateTime consultAt) {
    return new ConsultDTO(consultId, userId, title, kind, interest, consultAt);
  }

}

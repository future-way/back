package com.team.futureway.consult.request;

import com.team.futureway.consult.dto.ConsultDTO;

import java.time.LocalDateTime;

public record ConsultResponse(Long consultId, Long userId, LocalDateTime consultAt) {

  public static ConsultResponse of(ConsultDTO consultDTO) {
    return new ConsultResponse(consultDTO.getConsultId(), consultDTO.getUserId(), consultDTO.getConsultAt());
  }
}

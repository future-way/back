package com.team.futureway.consult.service;

import com.team.futureway.consult.dto.ConsultDTO;
import com.team.futureway.consult.entity.Consult;
import com.team.futureway.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConsultService {

  private final ConsultRepository consultRepository;

  public ConsultDTO save(Long userId, String kind, String interest) {
    Consult consult = Consult.of(null, userId, "", kind, interest, LocalDateTime.now());
    Consult result = consultRepository.save(consult);

    return ConsultDTO.of(
        result.getConsultId(),
        result.getUserId(),
        "",
        result.getKind(),
        result.getInterest(),
        result.getConsultAt());
  }

  public List<Consult> getConsultAll(){
    return consultRepository.findAll();
  }

}

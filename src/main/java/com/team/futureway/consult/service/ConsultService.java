package com.team.futureway.consult.service;

import com.team.futureway.consult.entity.Consult;
import com.team.futureway.consult.repository.ConsultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConsultService {

  private final ConsultRepository consultRepository;

  public List<Consult> getConsultAll(){
    return consultRepository.findAll();
  }

}

package com.team.futureway.consult.controller;

import com.team.futureway.consult.dto.ConsultDTO;
import com.team.futureway.consult.request.ConsultRequest;
import com.team.futureway.consult.request.ConsultResponse;
import com.team.futureway.consult.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/consult")
@RestController
public class ConsultController {

  private final ConsultService consultService;

  @PutMapping("/save")
  public ResponseEntity<?> getConsultAll(@RequestBody ConsultRequest request) {
    ConsultDTO consultDTO = consultService.save(request.userId(), request.kind(), request.interest());
    return ResponseEntity.ok(ConsultResponse.of(consultDTO));
  }

}

package com.team.futureway.consult.controller;

import com.team.futureway.consult.service.ConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/consult")
@RestController
public class ConsultController {

  private final ConsultService consultService;

  @GetMapping("")
  public ResponseEntity<?> getConsultAll() {
    return ResponseEntity.ok(consultService.getConsultAll());
  }

}

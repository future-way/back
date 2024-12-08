package com.team.futureway.prompt.controller;

import com.team.futureway.prompt.dto.QuestionDto;
import com.team.futureway.prompt.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prompt")
public class PromptController {

  private final GeminiService geminiService;

  @PostMapping("/question")
  public String requestGeminiAPI(@RequestBody QuestionDto dto) {
    return geminiService.callGeminiAPI(dto);
  }

}

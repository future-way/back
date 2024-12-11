package com.team.futureway.prompt.controller;

import com.team.futureway.prompt.dto.QuestionDto;
import com.team.futureway.prompt.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/prompt")
public class PromptController {

  private final PromptService promptService;

  @PostMapping("/question")
  public String requestGeminiAPI(@RequestBody QuestionDto dto) {
    return promptService.callGeminiAPI(dto);
  }

}

package com.team.futureway.gemini.controller;

import com.team.futureway.gemini.dto.GeminiQuestionDTO;
import com.team.futureway.gemini.response.GeminiQuestionResponse;
import com.team.futureway.gemini.response.GeminiRequest;
import com.team.futureway.gemini.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @GetMapping
    public ResponseEntity<GeminiQuestionResponse> gemini(@RequestBody GeminiRequest request) {
        GeminiQuestionDTO result = geminiService.getQuestionMessage(request.userId());
        return ResponseEntity.ok(GeminiQuestionResponse.of(result));
    }

}

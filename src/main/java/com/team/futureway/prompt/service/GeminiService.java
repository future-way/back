package com.team.futureway.prompt.service;

import com.team.futureway.prompt.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

  @Value("${gemini.api.key}")
  private String GEMINI_KEY;

  @Value("${gemini.api.url}")
  private String SERVICE_URL;

  public String callGeminiAPI(QuestionDto questionDto) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // 스크립트 작성.
    StringBuffer jsonQuestion = new StringBuffer()
        // 데이터 형태
        .append("{\"contents\":[{\"parts\":[{\"text\":\"")
        // 공통
        .append("홀랜드 적성검사와 사용자 전공을 기반으로 사용자의 적성 탐색에 도움을 주려고해.")
        .append("사용자의 적성 파악에 도움되는 질문 3가지를 알려줘. ")

        .append(questionDto.getQuestion())

        .append("답변 시 공손한 어조로 답변을 해줘.")
        .append("답변은 간단하게 3가지의 선택지만 제공해줘")
        .append("각 선택지는 30자를 초과하면 안되고,")
        .append("각 선택지 사이에는 ^로 구분 문자를 넣어줘")
        .append("총 100자를 초과하면 안돼.")
        // 데이터 형태
        .append("\"}]}]}");

    // HttpEntity 생성
    HttpEntity<String> entity = new HttpEntity<>(jsonQuestion.toString(), headers);

    // RestTemplate 사용
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response = restTemplate.exchange(SERVICE_URL + GEMINI_KEY, HttpMethod.POST, entity, String.class);

    return response.getBody();
  }

}

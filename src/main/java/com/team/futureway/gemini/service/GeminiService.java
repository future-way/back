package com.team.futureway.gemini.service;

import com.team.futureway.gemini.util.ParseGeminiMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GeminiService {

  @Value("${gemini.api.key}")
  private String GEMINI_KEY;

  @Value("${gemini.api.url}")
  private String SERVICE_URL;

  private final ParseGeminiMessageUtil parseGeminiMessageUtil;

  public String getNewQuestion(String consultMessage) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String consultData = consultMessage.replaceAll("\r?\n", "").replaceAll("\"", "").trim();

    // 스크립트 작성.
    StringBuffer json = new StringBuffer()
        // 데이터 형태
        .append("{\"contents\":[{\"parts\":[{\"text\":\"")
        // 공통
        .append(consultData)
        // 데이터 형태
        .append("\"}]}]}");

    // HttpEntity 생성
    HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);

    // RestTemplate 사용
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response = restTemplate.exchange(SERVICE_URL + GEMINI_KEY, HttpMethod.POST, entity, String.class);
    // API 응답을 JSON 파싱

    String responseBody = response.getBody();

    return parseGeminiMessageUtil.parseMessage(responseBody);
  }
}

package com.team.futureway.gemini.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ParseGeminiMessageUtil {

  public String parseMessage(String response) {
    try{
      ObjectMapper objectMapper = new ObjectMapper();

      JsonNode rootNode = objectMapper.readTree(response);

      // "candidates" 배열에서 첫 번째 항목을 가져오기
      JsonNode candidatesNode = rootNode.path("candidates").get(0);

      // "content" -> "parts" -> 첫 번째 항목 -> "text" 값 추출
      return candidatesNode.path("content").path("parts").get(0).path("text").asText();
    } catch (Exception e) {
      return null;
    }
  }
}

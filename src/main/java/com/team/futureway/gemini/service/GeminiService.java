package com.team.futureway.gemini.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.futureway.gemini.entity.AiConsultationHistory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String GEMINI_KEY;

    @Value("${gemini.api.url}")
    private String SERVICE_URL;

    public String getNewQuestion(String QuestionMessage, String answer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 스크립트 작성.
        StringBuffer jsonQuestion = new StringBuffer()
                // 데이터 형태
                .append("{\"contents\":[{\"parts\":[{\"text\":\"")
                // 공통
                .append("홀랜드 적성검사와 사용자 전공을 기반으로 사용자의 적성 탐색에 도움을 주려고해.")
                .append("사용자의 적성 파악에 도움되는 질문 3가지를 알려줘. ")

                .append("'" + QuestionMessage + "' 이 내용이 질문입니다.")
                .append("이게 사용자의 답변이야 '" + answer + "'")
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
        // API 응답을 JSON 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 응답을 JsonNode로 파싱
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // "candidates" 배열에서 첫 번째 항목을 가져오기
            JsonNode candidatesNode = rootNode.path("candidates").get(0);

            // "content" -> "parts" -> 첫 번째 항목 -> "text" 값 추출
            String text = candidatesNode.path("content").path("parts").get(0).path("text").asText();

            // 출력 또는 반환
            System.out.println("Extracted Text: " + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getSummary(List<AiConsultationHistory> aiConsultationHistoryList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String questionMessage = "";
        String answer = "";
        for (int i = 0; i < aiConsultationHistoryList.size(); i++) {
            questionMessage += (i + 1) + "번째 질문" + aiConsultationHistoryList.get(i).getQuestionMessage();
            answer += (i + 1) + "번째 답변" + aiConsultationHistoryList.get(i).getAnswer();
        }

        // 스크립트 작성.
        StringBuffer jsonQuestion = new StringBuffer()
                // 데이터 형태
                .append("{\"contents\":[{\"parts\":[{\"text\":\"")
                // 공통
                .append("'" + questionMessage + "' 은  question_message가 답변의 내용이고 "+answer+"은 답변입니다.")
                .append("예를 들면 이 상담받은 사람은 이런 저런 일을 흥미가 있고, 이런저런 일을 잘할거 같아요, 이런 저런일을 좋아해요 와 이사람에 대해 요약해줘.")
                .append("답변 시 공손한 어조로 답변을 해줘.")
                .append("총 100자를 초과하면 안돼.")
                // 데이터 형태
                .append("\"}]}]}");

        // HttpEntity 생성
        HttpEntity<String> entity = new HttpEntity<>(jsonQuestion.toString(), headers);

        // RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(SERVICE_URL + GEMINI_KEY, HttpMethod.POST, entity, String.class);
        // API 응답을 JSON 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 응답을 JsonNode로 파싱
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // "candidates" 배열에서 첫 번째 항목을 가져오기
            JsonNode candidatesNode = rootNode.path("candidates").get(0);

            // "content" -> "parts" -> 첫 번째 항목 -> "text" 값 추출
            String text = candidatesNode.path("content").path("parts").get(0).path("text").asText();

            // 출력 또는 반환
            System.out.println("Extracted Text: " + text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

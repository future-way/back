package com.team.futureway.consult.service;

import com.team.futureway.common.exception.CoreException;
import com.team.futureway.common.exception.ErrorType;
import com.team.futureway.consult.dto.SummaryDTO;
import com.team.futureway.consult.entity.Question;
import com.team.futureway.consult.entity.Summary;
import com.team.futureway.consult.repository.QuestionRepository;
import com.team.futureway.consult.repository.SummaryRepository;
import com.team.futureway.gemini.service.GeminiService;
import com.team.futureway.gemini.util.PromptUtil;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.entity.UserType;
import com.team.futureway.user.repository.UserRepository;
import com.team.futureway.user.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateAndReturnSummaryUseCase {

    private final UserRepository userRepository;

    private final QuestionRepository aiConsultationHistoryRepository;

    private final SummaryRepository aiConsultationSummaryHistoryRepository;

    private final PromptUtil promptUtil;

    private final GeminiService geminiService;

    private final UserTypeRepository userTypeRepository;

    public SummaryDTO execute(Long userId) {
        List<Question> aiConsultationHistoryList = aiConsultationHistoryRepository.findByUserId(userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));

        // gemini 에게 내용 전달후 상담 결과 요약 받기
        String summary = geminiService.getNewQuestion(promptUtil.getConsultHistorySummaryPrompt(user.getName(), aiConsultationHistoryList));
        String recommend = geminiService.getNewQuestion(promptUtil.getRecommendPrompt(user.getName(), summary));

        Summary aiConsultationSummaryHistory = Summary.of(null, userId, summary, recommend);
        Summary result = aiConsultationSummaryHistoryRepository.save(aiConsultationSummaryHistory);

        List<String> hollandTypes = extractDataInsideBraces(result.getSummary());

        if (hollandTypes.isEmpty()) {
            hollandTypes = extractHollandTypes(result.getSummary());
        }

        UserType userType = userTypeRepository.findByUserId(userId);

        String cleanedSummary = removeDataInsideBraces(result.getSummary());

        return SummaryDTO.of(result.getUserId(), cleanedSummary, recommend, userType.getUserType(), result.getCreatedDate(), hollandTypes);
    }

    public List<String> extractHollandTypes(String text) {
        List<String> hollandTypes = List.of("현실형", "탐구형", "예술형", "사회형", "진취형", "관습형");
        List<String> extractedTypes = new ArrayList<>();

        for (String type : hollandTypes) {
            if (text.contains(type)) {
                extractedTypes.add(type);
            }
        }

        return extractedTypes;
    }

    public List<String> extractDataInsideBraces(String input) {
        log.info("중괄호 제거: " + input);
        int startIndex = input.indexOf('{');
        int endIndex = input.indexOf('}');

        // 중괄호가 없으면 빈 리스트 반환
        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            return Collections.emptyList();
        }

        // 중괄호 안의 내용 추출
        String innerContent = input.substring(startIndex + 1, endIndex).trim();
        return new ArrayList<>(Arrays.asList(innerContent.split(",")));
    }

    public String removeDataInsideBraces(String input) {
        // 중괄호와 그 안의 내용을 삭제
        return input.replaceAll("\\{.*?\\}", "").trim();
    }
}

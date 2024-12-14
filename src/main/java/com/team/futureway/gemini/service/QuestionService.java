package com.team.futureway.gemini.service;

import com.team.futureway.common.exception.CoreException;
import com.team.futureway.common.exception.ErrorType;
import com.team.futureway.gemini.dto.AiConsultationSummaryHistoryDTO;
import com.team.futureway.gemini.dto.QuestionDTO;
import com.team.futureway.gemini.dto.UserTypeDTO;
import com.team.futureway.gemini.entity.AiConsultationHistory;
import com.team.futureway.gemini.entity.AiConsultationSummaryHistory;
import com.team.futureway.gemini.entity.UserType;
import com.team.futureway.gemini.entity.enums.UserTypeStatus;
import com.team.futureway.gemini.repository.AiConsultationHistoryRepository;
import com.team.futureway.gemini.repository.AiConsultationSummaryHistoryRepository;
import com.team.futureway.gemini.repository.UserTypeRepository;
import com.team.futureway.gemini.util.PromptUtil;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionService {

    private final UserRepository userRepository;

    private final AiConsultationHistoryRepository aiConsultationHistoryRepository;

    private final AiConsultationSummaryHistoryRepository aiConsultationSummaryHistoryRepository;

    private final PromptUtil promptUtil;

    private final GeminiService geminiService;

    private final UserTypeRepository userTypeRepository;

    public QuestionDTO getQuestionMessage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));

        UserType userType = userTypeRepository.findByUserId(userId);

        String consultType = promptUtil.setConsultTitle(userType);

        int questionNumber = 1; // 첫 질문은 1로 고정

        AiConsultationHistory aiConsultationHistory = AiConsultationHistory.of(null, userId, questionNumber, consultType, null);
        AiConsultationHistory result = aiConsultationHistoryRepository.save(aiConsultationHistory);

        String greetings = promptUtil.consultGreetings(user.getName(), result.getQuestionMessage());

        return QuestionDTO.of(result.getAiConsultationHistoryId(), result.getUserId(), result.getQuestionNumber(), greetings, result.getAnswer(), LocalDateTime.now());
    }

    @Transactional
    public QuestionDTO getNewQuestionMessage(QuestionDTO questionDTO) {

        AiConsultationHistory existingHistory = aiConsultationHistoryRepository.findById(questionDTO.getAiConsultationHistoryId())
                .orElseThrow(() -> new CoreException(ErrorType.CONSULTATION_HISTORY_NOT_FOUND, questionDTO.getAiConsultationHistoryId()));

        saveAnswerToHistory(existingHistory, questionDTO.getAnswer());

        String prompt = generatePrompt(questionDTO.getUserId());

        String newQuestionMessage = geminiService.getNewQuestion(prompt);

        AiConsultationHistory newHistory = createNewConsultationHistory(questionDTO.getUserId(), existingHistory.getQuestionNumber(), newQuestionMessage);
        AiConsultationHistory savedHistory = aiConsultationHistoryRepository.save(newHistory);

        return mapToQuestionDTO(savedHistory);
    }

    private void saveAnswerToHistory(AiConsultationHistory history, String answer) {
        history.setAnswer(answer);
        aiConsultationHistoryRepository.save(history);
    }

    private String generatePrompt(Long userId) {
        List<AiConsultationHistory> historyList = aiConsultationHistoryRepository.findByUserId(userId);
        String consultationHistory = promptUtil.extractConsultationHistory(historyList).toString();

        return consultationHistory + promptUtil.getPromptPrefix();
    }

    private AiConsultationHistory createNewConsultationHistory(Long userId, int questionNumber, String newQuestionMessage) {
        AiConsultationHistory newHistory = AiConsultationHistory.of(null, userId, questionNumber, newQuestionMessage, null);
        newHistory.incrementQuestionNumber();
        return newHistory;
    }

    private QuestionDTO mapToQuestionDTO(AiConsultationHistory history) {
        return QuestionDTO.of(
                history.getAiConsultationHistoryId(),
                history.getUserId(),
                history.getQuestionNumber(),
                history.getQuestionMessage(),
                history.getAnswer(),
                LocalDateTime.now()
        );
    }

    public AiConsultationSummaryHistoryDTO getSummary(Long userId) {
        List<AiConsultationHistory> aiConsultationHistoryList = aiConsultationHistoryRepository.findByUserId(userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));

        // gemini 에게 내용 전달후 상담 결과 요약 받기
        String summary = geminiService.getNewQuestion(promptUtil.getConsultHistoryPrompt(user.getName(), aiConsultationHistoryList));

        AiConsultationSummaryHistory aiConsultationSummaryHistory = AiConsultationSummaryHistory.of(null, userId, summary);
        AiConsultationSummaryHistory result = aiConsultationSummaryHistoryRepository.save(aiConsultationSummaryHistory);

        List<String> hollandTypes = extractDataInsideBraces(result.getSummary());

        UserType userType = userTypeRepository.findByUserId(userId);

        String cleanedSummary = removeDataInsideBraces(result.getSummary());

        return AiConsultationSummaryHistoryDTO.of(result.getUserId(), cleanedSummary, userType.getUserType(), result.getCreatedDate(), hollandTypes);
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

    public UserTypeDTO saveUserType(UserTypeDTO userTypeDTO) {
        UserType userType = UserType.of(null
                , userTypeDTO.getUserId()
                , userTypeDTO.getQuestion()
                , userTypeDTO.getSelectType()
                , userTypeDTO.getAnswer()
                , userTypeDTO.getUserType()
        );
        UserType result = userTypeRepository.save(userType);
        return userTypeDTO.of(result.getUserId(), result.getQuestion(), result.getSelectType(), result.getAnswer(), result.getUserType());
    }

}

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

        String firstMessage = promptUtil.getFirstConsult(user.getName(), userType);

        int questionNumber = 1; // 첫 질문은 1로 고정

        AiConsultationHistory aiConsultationHistory = AiConsultationHistory.of(null, userId, questionNumber, firstMessage, null);
        AiConsultationHistory result = aiConsultationHistoryRepository.save(aiConsultationHistory);

        return QuestionDTO.of(result.getAiConsultationHistoryId(), result.getUserId(), result.getQuestionNumber(), result.getQuestionMessage(), result.getAnswer());
    }

    @Transactional
    public QuestionDTO getNewQuestionMessage(QuestionDTO questionDTO) {

        AiConsultationHistory existingHistory = aiConsultationHistoryRepository.findById(questionDTO.getAiConsultationHistoryId())
                .orElseThrow(() -> new CoreException(ErrorType.CONSULTATION_HISTORY_NOT_FOUND, questionDTO.getAiConsultationHistoryId()));

        saveAnswerToHistory(existingHistory, questionDTO.getAnswer());

        String prompt = generatePrompt(questionDTO.getUserId(), questionDTO.getAnswer());

        String newQuestionMessage = geminiService.getNewQuestion(prompt);

        AiConsultationHistory newHistory = createNewConsultationHistory(questionDTO.getUserId(), existingHistory.getQuestionNumber(), newQuestionMessage);
        AiConsultationHistory savedHistory = aiConsultationHistoryRepository.save(newHistory);

        return mapToQuestionDTO(savedHistory);
    }

    private void saveAnswerToHistory(AiConsultationHistory history, String answer) {
        history.setAnswer(answer);
        aiConsultationHistoryRepository.save(history);
    }

    private String generatePrompt(Long userId, String answer) {
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
                history.getAnswer()
        );
    }

    public AiConsultationSummaryHistoryDTO getSummary(Long userId) {
        List<AiConsultationHistory> aiConsultationHistoryList = aiConsultationHistoryRepository.findByUserId(userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));

        // gemini 에게 내용 전달후 상담 결과 요약 받기
        String summary = geminiService.getNewQuestion(promptUtil.getConsultHistoryPrompt(user.getName(), aiConsultationHistoryList));

        AiConsultationSummaryHistory aiConsultationSummaryHistory = AiConsultationSummaryHistory.of(null, userId, summary);
        AiConsultationSummaryHistory result = aiConsultationSummaryHistoryRepository.save(aiConsultationSummaryHistory);

        return AiConsultationSummaryHistoryDTO.of(result.getUserId(), result.getSummary(), result.getCreatedDate());
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

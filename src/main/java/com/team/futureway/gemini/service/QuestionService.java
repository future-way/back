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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
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

        int questionNumber = 1; // 첫 질문은 1로 고정..

        AiConsultationHistory aiConsultationHistory = AiConsultationHistory.of(null, userId, questionNumber, firstMessage, null);
        AiConsultationHistory result = aiConsultationHistoryRepository.save(aiConsultationHistory);

        return QuestionDTO.of(result.getAiConsultationHistoryId(), result.getUserId(), result.getQuestionNumber(), result.getQuestionMessage(), result.getAnswer());
    }

    @Transactional
    public QuestionDTO getNewQuestionMessage(QuestionDTO questionDTO) {
        AiConsultationHistory aiConsultationHistory = aiConsultationHistoryRepository.findById(questionDTO.getAiConsultationHistoryId())
                .orElseThrow(() -> new CoreException(ErrorType.CONSULTATION_HISTORY_NOT_FOUND, questionDTO.getAiConsultationHistoryId()));

        // 답변 저장
        aiConsultationHistory.setAnswer(questionDTO.getAnswer());
        aiConsultationHistoryRepository.save(aiConsultationHistory);

        String prompt = promptUtil.getAnswerPrompt(questionDTO.getAnswer()) + promptUtil.getPromptPrefix();

        String newQuestionMessage = geminiService.getNewQuestion(prompt);

        AiConsultationHistory newAiConsultationHistory = AiConsultationHistory.of(null
                , questionDTO.getUserId()
                , aiConsultationHistory.getQuestionNumber()
                , newQuestionMessage
                , null);

        newAiConsultationHistory.incrementQuestionNumber();
        AiConsultationHistory result = aiConsultationHistoryRepository.save(newAiConsultationHistory);

        return QuestionDTO.of(result.getAiConsultationHistoryId(), result.getUserId(), result.getQuestionNumber(), result.getQuestionMessage(), result.getAnswer());
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

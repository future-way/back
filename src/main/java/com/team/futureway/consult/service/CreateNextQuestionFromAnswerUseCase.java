package com.team.futureway.consult.service;

import com.team.futureway.common.exception.CoreException;
import com.team.futureway.common.exception.ErrorType;
import com.team.futureway.consult.dto.QuestionDTO;
import com.team.futureway.consult.entity.Question;
import com.team.futureway.consult.repository.QuestionRepository;
import com.team.futureway.gemini.service.GeminiService;
import com.team.futureway.gemini.util.PromptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateNextQuestionFromAnswerUseCase {

    private final QuestionRepository aiConsultationHistoryRepository;

    private final PromptUtil promptUtil;

    private final GeminiService geminiService;

    @Transactional
    public QuestionDTO execute(QuestionDTO questionDTO) {

        Question existingHistory = aiConsultationHistoryRepository.findById(questionDTO.getQuestionId())
                .orElseThrow(() -> new CoreException(ErrorType.CONSULTATION_HISTORY_NOT_FOUND, questionDTO.getQuestionId()));

        saveAnswerToHistory(existingHistory, questionDTO.getAnswer());

        String prompt = generatePrompt(questionDTO.getUserId());

        String newQuestionMessage = geminiService.getNewQuestion(prompt);

        String cleanedSummary = removeDataInsideBraces(newQuestionMessage);

        Question newHistory = createNewConsultationHistory(questionDTO.getUserId(), existingHistory.getQuestionNumber(), cleanedSummary);
        Question savedHistory = aiConsultationHistoryRepository.save(newHistory);

        return mapToQuestionDTO(savedHistory);
    }

    private void saveAnswerToHistory(Question history, String answer) {
        history.setAnswer(answer);
        aiConsultationHistoryRepository.save(history);
    }

    private String generatePrompt(Long userId) {
        List<Question> historyList = aiConsultationHistoryRepository.findByUserId(userId);
        String consultationHistory = promptUtil.extractConsultationHistory(historyList).toString();

        return consultationHistory + promptUtil.getPromptPrefix();
    }

    private Question createNewConsultationHistory(Long userId, int questionNumber, String newQuestionMessage) {
        Question newHistory = Question.of(null, userId, questionNumber, newQuestionMessage, null);
        newHistory.incrementQuestionNumber();
        return newHistory;
    }

    public String removeDataInsideBraces(String input) {
        // 중괄호와 그 안의 내용을 삭제
        return input.replaceAll("\\{.*?\\}", "").trim();
    }

    private QuestionDTO mapToQuestionDTO(Question history) {
        return QuestionDTO.of(
                history.getQuestionId(),
                history.getUserId(),
                history.getQuestionNumber(),
                history.getQuestionMessage(),
                history.getAnswer(),
                LocalDateTime.now()
        );
    }
}

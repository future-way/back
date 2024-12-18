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

    private final QuestionRepository questionRepository;

    private final PromptUtil promptUtil;

    private final GeminiService geminiService;

    @Transactional
    public QuestionDTO execute(QuestionDTO questionDTO) {

        Question question = questionRepository.findById(questionDTO.getQuestionId())
                .orElseThrow(() -> new CoreException(ErrorType.CONSULTATION_HISTORY_NOT_FOUND, questionDTO.getQuestionId()));

        saveAnswerToHistory(question, questionDTO.getAnswer());

        String prompt = generatePrompt(questionDTO.getUserId());

        String createQuestion = geminiService.getNewQuestion(prompt);

        question.removeBracesAndTextFromQuestion(createQuestion);

        Question newQuestion = createNewConsultationHistory(questionDTO.getUserId(), question.getQuestionNumber(), question.getQuestionMessage());

        Question savedHistory = questionRepository.save(newQuestion);

        return mapToQuestionDTO(savedHistory);
    }

    private void saveAnswerToHistory(Question history, String answer) {
        history.setAnswer(answer);
        questionRepository.save(history);
    }

    private String generatePrompt(Long userId) {
        List<Question> historyList = questionRepository.findByUserId(userId);
        String consultationHistory = promptUtil.extractConsultationHistory(historyList).toString();

        return consultationHistory + promptUtil.getPromptPrefix();
    }

    private Question createNewConsultationHistory(Long userId, int questionNumber, String newQuestionMessage) {
        Question newHistory = Question.of(null, userId, questionNumber, newQuestionMessage, null);
        newHistory.incrementQuestionNumber();
        return newHistory;
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

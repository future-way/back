package com.team.futureway.consult.service;

import com.team.futureway.common.exception.CoreException;
import com.team.futureway.common.exception.ErrorType;
import com.team.futureway.consult.dto.QuestionDTO;
import com.team.futureway.consult.entity.Question;
import com.team.futureway.consult.repository.QuestionRepository;
import com.team.futureway.gemini.util.PromptUtil;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.entity.UserType;
import com.team.futureway.user.repository.UserRepository;
import com.team.futureway.user.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateFirstQuestionUseCase {

    private final UserRepository userRepository;

    private final QuestionRepository aiConsultationHistoryRepository;

    private final PromptUtil promptUtil;

    private final UserTypeRepository userTypeRepository;

    public QuestionDTO execute(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));

        UserType userType = userTypeRepository.findByUserId(userId);

        String consultType = promptUtil.setConsultTitle(userType);

        int questionNumber = 1; // 첫 질문은 1로 고정

        Question aiConsultationHistory = Question.of(null, userId, questionNumber, consultType, null);
        Question result = aiConsultationHistoryRepository.save(aiConsultationHistory);

        String greetings = promptUtil.consultGreetings(user.getName(), result.getQuestionMessage());

        return QuestionDTO.of(result.getQuestionId(), result.getUserId(), result.getQuestionNumber(), greetings, result.getAnswer(), LocalDateTime.now());
    }
}

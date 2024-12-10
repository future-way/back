package com.team.futureway.gemini.service;

import com.team.futureway.common.exception.CoreException;
import com.team.futureway.common.exception.ErrorType;
import com.team.futureway.gemini.dto.GeminiQuestionDTO;
import com.team.futureway.gemini.entity.AiConsultationHistory;
import com.team.futureway.gemini.repository.AiConsultationHistoryRepository;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GeminiService {

    private final UserRepository userRepository;
    private final AiConsultationHistoryRepository aiConsultationHistoryRepository;

    public GeminiQuestionDTO getQuestionMessage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));
        String questionMessage =
                "안녕하세요! 제 이름은 모모예요, 관심 분야는 있는데" +
                "정확히 어떤 일을 해야 할지 모르겠어서 혼란스러우신 거 같아요." +
                "제가 앞으로 몇가지 질문을 할 텐데 답변해주시면 원하는 진로를 찾을 수 있을거 같아요!" +
                "^" + user.getName()+"님은 어떤 분야에 관심이 있으신가요?" +
                "구체적으로 말해주실수록 더 좋아요! 산업, 직군, 세부 직업 이름 등 자세히 말씀해주세요.";

        int QuestionNumber = 1; // 첫 질문은 1로 고정..

        AiConsultationHistory aiConsultationHistory = AiConsultationHistory.of(null, userId, QuestionNumber, questionMessage, null);
        AiConsultationHistory result = aiConsultationHistoryRepository.save(aiConsultationHistory);

        return GeminiQuestionDTO.of(result.getAiConsultationHistoryId(),result.getUserId(), result.getQuestionNumber(), result.getQuestionMessage(), result.getAnswer());
    }
}

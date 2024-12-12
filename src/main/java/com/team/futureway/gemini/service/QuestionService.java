package com.team.futureway.gemini.service;

import com.team.futureway.common.exception.CoreException;
import com.team.futureway.common.exception.ErrorType;
import com.team.futureway.gemini.dto.AiConsultationSummaryHistoryDTO;
import com.team.futureway.gemini.dto.QuestionDTO;
import com.team.futureway.gemini.dto.UserTypeDTO;
import com.team.futureway.gemini.entity.AiConsultationHistory;
import com.team.futureway.gemini.entity.AiConsultationSummaryHistory;
import com.team.futureway.gemini.entity.UserType;
import com.team.futureway.gemini.repository.AiConsultationHistoryRepository;
import com.team.futureway.gemini.repository.AiConsultationSummaryHistoryRepository;
import com.team.futureway.gemini.repository.UserTypeRepository;
import com.team.futureway.gemini.util.PromptUtil;
import com.team.futureway.user.entity.User;
import com.team.futureway.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

  public QuestionDTO getQuestionMessage(Long userId, String kind, String interest) {
  User user = userRepository.findById(userId).orElseThrow(() -> new CoreException(ErrorType.USER_NOT_FOUND, userId));
  String startMessage = promptUtil.getStartConsult(user.getName());

  String prompt = promptUtil.getPromptMultiPrefix();

  String questionMessage = geminiService.getNewQuestion(prompt);

  int questionNumber = 1; // 첫 질문은 1로 고정..
  String storeQuestion = "홀랜드 적성 검사 기반 사용자 전공 및 사용자 진로 상담을 진행합니다.";

  AiConsultationHistory aiConsultationHistory = AiConsultationHistory.of(null, userId, questionNumber, storeQuestion, null, kind, interest);
  AiConsultationHistory result = aiConsultationHistoryRepository.save(aiConsultationHistory);

  List<String> multiMessage = checkMultiMessage(questionMessage);

  if (multiMessage.size() > 1) {
    startMessage += "아래 선택지 중 하나를 선택해주세요!";
  } else {
    startMessage += "어떤 분야에 관심이 있나요?";
  }

  return QuestionDTO.of(
      result.getAiConsultationHistoryId(),
      result.getUserId(),
      result.getQuestionNumber(),
      startMessage,
      result.getAnswer(),
      multiMessage);
  }

  @Transactional
  public QuestionDTO getNewQuestionMessage(QuestionDTO questionDTO) {
    AiConsultationHistory aiConsultationHistory = aiConsultationHistoryRepository.findById(questionDTO.getAiConsultationHistoryId())
        .orElseThrow(() -> new CoreException(ErrorType.CONSULTATION_HISTORY_NOT_FOUND, questionDTO.getAiConsultationHistoryId()));

    // 답변 저장
    aiConsultationHistory.setAnswer(questionDTO.getAnswer());
    aiConsultationHistoryRepository.save(aiConsultationHistory);

    String prompt = promptUtil.getPromptPrefix() + promptUtil.getAnswerPrompt(questionDTO.getAnswer());

    // 새로운 질문 생성 > gemini
    String newQuestionMessage = geminiService.getNewQuestion(prompt);

    AiConsultationHistory newAiConsultationHistory = AiConsultationHistory.of(null
        , questionDTO.getUserId()
        , aiConsultationHistory.getQuestionNumber()
        , newQuestionMessage
        , null
        , aiConsultationHistory.getInterest()
        , aiConsultationHistory.getKind());

    newAiConsultationHistory.incrementQuestionNumber();
    AiConsultationHistory result = aiConsultationHistoryRepository.save(newAiConsultationHistory);

    List<String> multiMessage = checkMultiMessage(newQuestionMessage);

    return QuestionDTO.of(
        result.getAiConsultationHistoryId(),
        result.getUserId(),
        result.getQuestionNumber(),
        result.getQuestionMessage(),
        result.getAnswer(),
        multiMessage);
  }

  public AiConsultationSummaryHistoryDTO getSummary(Long userId) {
    List<AiConsultationHistory> aiConsultationHistoryList = aiConsultationHistoryRepository.findByUserId(userId);

    // gemini 에게 내용 전달후 상담 결과 요약 받기
    String summary = promptUtil.getConsultHistoryPrompt(aiConsultationHistoryList);

    AiConsultationSummaryHistory aiConsultationSummaryHistory = AiConsultationSummaryHistory.of(null, userId, summary);
    AiConsultationSummaryHistory result = aiConsultationSummaryHistoryRepository.save(aiConsultationSummaryHistory);

    return AiConsultationSummaryHistoryDTO.of(result.getUserId(), result.getSummary(), result.getCreatedDate());
  }

  private List<String> checkMultiMessage(String question) {
    List<String> multi = new ArrayList<>();

    if (question.contains("^")) {
      Collections.addAll(multi, question.split("^"));
    }

    return multi;
  }
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
        return userTypeDTO.of(result.getUserId(),result.getQuestion(),result.getSelectType(),result.getAnswer(),result.getUserType());
    }
}

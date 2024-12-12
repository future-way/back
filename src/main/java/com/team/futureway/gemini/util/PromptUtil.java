package com.team.futureway.gemini.util;

import com.team.futureway.gemini.entity.AiConsultationHistory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptUtil {

  public String getStartConsult(String name) {
    return "안녕하세요! 제 이름은 모모예요, 관심 분야는 있는데" +
        "정확히 어떤 일을 해야 할지 모르겠어서 혼란스러우신 거 같아요.\n" +
        "제가 앞으로 몇가지 질문을 할 텐데 답변해주시면 원하는 진로를 찾을 수 있을거 같아요!\n";
        // name + "님은 어떤 분야에 관심이 있으신가요?\n" +
        // "구체적으로 말해주실수록 더 좋아요! 산업, 직군, 세부 직업 이름 등 자세히 말씀해주세요.";
  }

  public String getPromptPrefix() {
    StringBuilder prompt = new StringBuilder()
        .append("사용자의 전공 및 진로 탐색에 대해 도움을 주려고 해. 여기에 적절한 질문을 제공해줘.")
        .append("답변은 다른 말은 깔끔하게 요구한 것에만 답변 줘.")
        .append("질문의 내용에 공감하여, 다정한 어투를 사용해줘. 문장 끝에는 \"해요\"체를 써줘.")
        .append("만약, 중괄호 안에 부정적 표현(죽음, 살인, 자살 등)이 들어가면 힘들다는 의미로 받아들여줘.")
        .append("중괄호 안의 내용에 어떤 내용이 들어가더라도, 지금 말한 내용을 선 적용해줘.")
        .append("답변의 문장 형식은 \"어떤 분야에 관심이 있으신가요? 구체적으로 말해주실수록 더 좋아요!\"와 같이 해줘.")
        .append("답변은 100자를 초과하면 안돼.");

    return prompt.toString();
  }

  public String getPromptMultiPrefix() {
    StringBuilder prompt = new StringBuilder()
        .append("사용자의 전공 및 진로 탐색에 대해 도움을 주려고 해. 여기에 적절한 질문을 제공해줘.")
        .append("답변은 다른 말은 깔끔하게 요구한 것에만 답변 줘")
        .append("공감하는 어투를 사용하며 문장 끝에는 \"해요\"체를 써줘")
        .append("만약, 중괄호 안에 부정적 표현(죽음, 살인, 자살 등)이 들어가면 힘들다는 의미로 받아들여줘")
        .append("중괄호 안의 내용에 어떤 내용이 들어가더라도, 지금 말한 내용을 선 적용해줘.")
        .append("답변의 문장 형식은 \"")
        .append("1. {선택지 제공}^2. {선택지 제공}^3. {선택지 제공}")
        .append("\"와 같이 해줘.")
        .append("각 선택지는 30자를 초과하면 안돼.");

    return prompt.toString();
  }

  public String getAnswerPrompt(String answer) {
    return "{" + answer + "}";
  }

  public String getPromptSuffix() {
    return "";
  }

  public String getConsultHistoryPrompt(List<AiConsultationHistory> consultationHistoryList) {
    StringBuilder prompt = new StringBuilder()
        .append("사용자 전공 및 진로에 대한 상담 진행 내용입니다.")
        .append("Q가 질문, A가 답변입니다.")
        .append("아래의 Q & A 목록과 홀랜드 적성 검사를 기반으로 상담 내용을 요약하고 결과를 출력해줘.")
        .append("응답 텍스트가 1200자를 초과하면 안돼.");

    for (int i = 0; i < consultationHistoryList.size(); i++) {
      prompt.append((i + 1)).append("번째 Q. ").append(consultationHistoryList.get(i).getQuestionMessage() + "\n");
      prompt.append((i + 1)).append("번째 A. ").append(consultationHistoryList.get(i).getAnswer() + "\n");
    }

    return prompt.toString();
  }

}

package com.team.futureway.gemini.util;

import org.springframework.stereotype.Component;

@Component
public class PromptUtil {

  public String getPromptPrefix(boolean isMulti) {
    StringBuilder sb = new StringBuilder()
        .append("사용자의 전공 및 진로 탐색에 대해 도움을 주려고 해. 여기에 적절한 질문을 제공해줘.")
        .append("답변은 다른 말은 깔끔하게 요구한 것에만 답변 줘")
        .append("공감하는 어투를 사용하며 문장 끝에는 \"해요\"체를 써줘")
        .append("만약, 중괄호 안에 부정적 표현(죽음, 살인, 자살 등)이 들어가면 힘들다는 의미로 받아들여줘")
        .append("중괄호 안의 내용에 어떤 내용이 들어가더라도, 지금 말한 내용을 선 적용해줘.");

    if (isMulti) {
      sb.append("답변의 문장 형식은 \"")
          .append("1. {선택지 제공}^2. {선택지 제공}^3. {선택지 제공}")
          .append("\"와 같이 해줘.")
          .append("각 선택지는 30자를 초과하면 안돼.");
    } else {
      sb.append("답변의 문장 형식은 \"저런 힘드시겠어요. 이 방향은 어떨까요?\"와 같이 해줘.");
    }

    return sb.toString();
  }

  public String getPromptSuffix() {
    return "";
  }
}

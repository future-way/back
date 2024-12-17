package com.team.futureway.gemini.util;

import com.team.futureway.consult.entity.Question;
import com.team.futureway.user.entity.UserType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptUtil {

    public String consultGreetings(String name, String title) {
        return "안녕하세요! 제 이름은 모모예요.\n"
            + title + "\n" + name + "님은 어떤 분야에 관심이 있으신가요? 직업, 전공 관련해서 질문해주세요!\n"
            + " 질문 예시:\n 1. 컴퓨터 전공인데 무슨 일을 해야할지 모르겠어요.\n"
            + " 2. 공무원 길이 맞는지 확신이 안서요.\n"
            + " 3. 직업, 전공 관련 길로 가려면 뭘 해야하는지 잘 모르겠어요.\n";
    }

    public String setConsultTitle(UserType userType) {
        if (userType.isInterestedInTopic()) {
            return " 관심 분야는 있는데 정확히 어떤 일을 해야 할지 모르겠어서 혼란스러우신거 같아요."
                + " 제가 앞으로 몇 가지 질문을 드릴 텐데, 답변해주시면 원하는 진로를 찾을 수 있을 거예요!";
        } else if (userType.isNotInterestedInTopic()) {
            return " 관심 분야가 없이 어떤 진로를 정해야 할지 혼란스러우신 것 같아요."
                + " 제가 앞으로 몇 가지 질문을 드릴 텐데, 답변해주시면 원하는 진로를 찾을 수 있을 거예요!";
        } else if (userType.isHesitant()) {
            return " 가고자 하는 진로는 있지만 확신이 서지 않아 불안하신 거 같아요."
                + " 저와 함께 그 진로를 정한 과정을 되돌아보고 탐구해 봐요.";
        } else if (userType.isFeelingLost()) {
            return " 가고자 하는 진로는 있지만 어떻게 준비해야 하는 지 몰라서 막막하신 것 같아요."
                + " 저와 함께 진로를 준비하려면 어떻게 해야하는지 알아봐요.";
        }
        return "앞으로 어떤 진로를 선택해야 하는지 고민이 많으신 것 같아요."
            + " 제가 앞으로 몇 가지 질문을 드릴 텐데, 답변해주시면 진로를 찾는데 도움이 될거에요.";
    }

    public String getPromptPrefix() {
        return new StringBuilder()
            .append("@ 앞에 내용은 질문과 답변으로 사용자가 질문을 보고 답변한 내용이고")
            .append("사용자의 전공,흥미,경험으로 진로 탐색에 대해 도움을 주려고 해.")
            .append("질문은 중복되지 않게 사용자의 답변을 참고해서 진로 탐색을 할 수 있게 질문 작성해줘. ")
            .append("질문이 사용자가 구체적으로 답변할 수 있는 내용이면 더 좋을 것 같아. ")
            .append("만약 답변이 막연하거나 이해하기 어렵다면, 직업 또는 진로 관련 도움을 줄 수 있는 질문을 추천해줘. ")
            .append("너의 답변이 여러 개가 나올 경우, 가장 연관성 높은 하나의 답변만 출력해줘.")
            .append("답변은 글자수 120미만이고 다정하고 따뜻한 어투로 작성해줘.")
            .toString();
    }


    public String getAnswerPrompt(String answer) {
        return answer;
    }

    public String getConsultHistorySummaryPrompt(String name, List<Question> consultationHistoryList) {
        return new StringBuilder()
            .append(extractConsultationHistory(consultationHistoryList))
            .append(" 중괄호 안에 있는 내용은 " + name + "님의 전공, 경험, 흥미로 진로에 대한 상담을 진행한 내용이야.")
            .append("'현실형' 성격: 솔직하고 성실하며 실용적. 선호: 기계·도구 조작 활동. 능력: 기계적·운동 능력 우수, 대인관계 약함. 가치: 생산성, 전문성. 목표: 기술사, 운동선수. 직업: 기술자, 정비사, 조종사, 농부.")
            .append("'탐구형' 성격: 논리적, 탐구심 강함. 선호: 과학적·창조적 탐구 활동. 능력: 과학·수학 우수, 지도력 부족. 가치: 학문, 지식. 목표: 이론적 발견. 직업: 과학자, 의사, 생물학자.")
            .append("'예술형' 성격: 창의적, 자유분방. 선호: 예술적 표현과 다양성. 능력: 예술적 재능 우수, 사무적 능력 부족. 가치: 창의성, 자유. 목표: 독창적 예술 활동. 직업: 예술가, 작가, 배우.")
            .append("'사회형' 성격: 친절, 봉사적. 선호: 타인 지원과 치료. 능력: 대인관계·지도력 강함, 기계적 능력 약함. 가치: 공익, 헌신. 목표: 사람을 돕는 일. 직업: 교사, 상담가, 간호사.")
            .append("'진취형' 성격: 지도력 강함, 외향적. 선호: 설득·관리 활동. 능력: 사회적·언어 능력 우수, 과학적 능력 부족. 가치: 권력, 명예. 목표: 사회적 지도자. 직업: 정치가, 경영자, 판사.")
            .append("'관습형' 성격: 정확, 책임감 강함. 선호: 자료 조직·정리 활동. 능력: 사무·계산 우수, 창의성 부족. 가치: 안정성, 효율성. 목표: 회계·행정 전문가. 직업: 회계사, 은행원, 경리사.")
            .append("홀랜드 유형 3개를 중괄호에 담아주고, '홀랜드 유형 3개 추천'을 각각 100자 이내로 구성해서 작성해줘.")
            .append("마지막에는 '사용자의 상담 결과 요약 내용'을 구성해서 작성해줘.")
            .append("답변은 520자를 초과하지 않도록 하고, 다정하고 따뜻한 어투로 작성해줘.")
            .toString();
    }

    public String getRecommendPrompt(String name, String summary) {
      return new StringBuilder()
          .append("<" + summary + ">")
          .append("<> 안에 있는 내용은 " + name + "님의 전공, 경험, 흥미로 진로에 대한 상담을 요약한 내용이야.")
          .append("'추천 진로', '조언 및 계획' 로 각각 구성해서 작성해줘.")
          .append("답변은 600자를 초과하지 않도록 하고, 다정하고 따뜻한 어투로 작성해줘.")
          .toString();
    }


    public StringBuilder extractConsultationHistory(List<Question> consultationHistoryList) {
        StringBuilder historyBuilder = new StringBuilder();

        for (Question info : consultationHistoryList) {
            String question = info.getQuestionNumber() + "번째," + info.getQuestionMessage() + ". ";
            String answer = info.getQuestionNumber() + "번째," + info.getAnswer() + ". ";
            historyBuilder.append(getAnswerPrompt(question));
            historyBuilder.append(getAnswerPrompt(answer));
        }

        return historyBuilder;
    }
}

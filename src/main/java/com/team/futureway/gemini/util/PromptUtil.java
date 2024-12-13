package com.team.futureway.gemini.util;

import com.team.futureway.gemini.entity.AiConsultationHistory;
import com.team.futureway.gemini.entity.UserType;
import com.team.futureway.gemini.entity.enums.UserTypeStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromptUtil {

    public String getFirstConsult(String name, UserType userType) {
        String returnMessage = "안녕하세요 제 이름은 모모예요.";

        if (userType.isInterestedInTopic()) {
            returnMessage += " 관심 분야는 있는데 정확히 어떤 일을 해야 할지 모르겠어서 혼란스러우신거 같아요."
                    + " 제가 앞으로 몇 가지 질문을 드릴 텐데, 답변해주시면 원하는 진로를 찾을 수 있을 거예요!";
        } else if (userType.isNotInterestedInTopic()) {
            returnMessage += " 관심 분야가 없이 어떤 진로를 정해야 할지 혼란스러우신 것 같아요."
                    + " 제가 앞으로 몇 가지 질문을 드릴 텐데, 답변해주시면 원하는 진로를 찾을 수 있을 거예요!";
        } else if (userType.isHesitant()) {
            returnMessage += " 가고자 하는 진로는 있지만 확신이 서지 않아 불안하신 거 같아요."
                    + " 저와 함께 그 진로를 정한 과정을 되돌아보고 탐구해 봐요.";
        } else if (userType.isFeelingLost()) {
            returnMessage += " 가고자 하는 진로는 있지만 어떻게 준비해야 하는 지 몰라서 막막하신 것 같아요."
                    + " 저와 함께 진로를 준비하려면 어떻게 해야하는지 알아봐요.";
        }

        returnMessage += "^" + name + "님은 어떤 분야에 관심이 있으신가요? 직업, 전공 관련해서 질문해주세요!"
                + " 질문 예시: 1.컴퓨터전공인데 무슨일을 해야할지 모르겟어요."
                + " 2. 공무원 길이 맞는지 확신이 안서요."
                + " 3. 직업, 전공 관련 길로 가려면 뭘 해야하는지 잘 모르겠어요.";
        return returnMessage;
    }

    public String getPromptPrefix() {
        StringBuilder prompt = new StringBuilder()
                .append(" 사용자의 전공 및 진로 탐색에 대해 도움을 주려고 해. ")
                .append("답변 내용에 공감하며 다정한 어투로 질문과 답변을 '해요' 체로 작성해줘. ")
                .append("중괄호 내용은 사용자가 답변한 내용이니 참고만 해줘 ")
                .append("사용자의 답변을 바탕으로 적절한 질문을 작성해줘.  ")
                .append("탐구형, 의견 요청형, 가설형, 추론형 질문 중에서 상황에 가장 적합한 질문을 선택하되, 어떤 형 질문인지 언급하지 말아줘.")
                .append("질문이 사용자가 구체적으로 답변할 수 있는 내용이면 더 좋을 것 같아. ")
                .append("만약 답변이 막연하거나 이해하기 어렵다면, 직업 정보나 진로 관련 도움을 줄 수 있는 질문을 추천해줘. ")
                .append("답변은 100자를 초과하지 않도록 하고, 다정하고 따뜻한 어투로 작성해줘.");

        return prompt.toString();
    }


    public String getAnswerPrompt(String answer) {
        return "{" + answer + "}";
    }

    public String getConsultHistoryPrompt(String name, List<AiConsultationHistory> consultationHistoryList) {
        StringBuilder prompt = new StringBuilder();

        for (AiConsultationHistory info : consultationHistoryList) {
            String question = info.getQuestionNumber() + "번째 질문" + info.getQuestionMessage();
            String answer = info.getQuestionNumber() + "번째 답변" + info.getAnswer();
            prompt.append(getAnswerPrompt(question));
            prompt.append(getAnswerPrompt(answer));
        }

        prompt.append("AI 프롬프트 설정: 중괄호 안에 있는 내용은 " + name + "님의 전공 및 진로에 대한 상담을 진행한 내용입니다.");
        prompt.append("'현실형' 성격: 솔직하고 성실하며 실용적. 선호: 기계·도구 조작 활동. 능력: 기계적·운동 능력 우수, 대인관계 약함. 가치: 생산성, 전문성. 목표: 기술사, 운동선수. 직업: 기술자, 정비사, 조종사, 농부.");
        prompt.append("'탐구형' 성격: 논리적, 탐구심 강함. 선호: 과학적·창조적 탐구 활동. 능력: 과학·수학 우수, 지도력 부족. 가치: 학문, 지식. 목표: 이론적 발견. 직업: 과학자, 의사, 생물학자.");
        prompt.append("'예술형' 성격: 창의적, 자유분방. 선호: 예술적 표현과 다양성. 능력: 예술적 재능 우수, 사무적 능력 부족. 가치: 창의성, 자유. 목표: 독창적 예술 활동. 직업: 예술가, 작가, 배우.");
        prompt.append("'사회형' 성격: 친절, 봉사적. 선호: 타인 지원과 치료. 능력: 대인관계·지도력 강함, 기계적 능력 약함. 가치: 공익, 헌신. 목표: 사람을 돕는 일. 직업: 교사, 상담가, 간호사.");
        prompt.append("'진취형' 성격: 지도력 강함, 외향적. 선호: 설득·관리 활동. 능력: 사회적·언어 능력 우수, 과학적 능력 부족. 가치: 권력, 명예. 목표: 사회적 지도자. 직업: 정치가, 경영자, 판사.");
        prompt.append("'관습형' 성격: 정확, 책임감 강함. 선호: 자료 조직·정리 활동. 능력: 사무·계산 우수, 창의성 부족. 가치: 안정성, 효율성. 목표: 회계·행정 전문가. 직업: 회계사, 은행원, 경리사.");
        prompt.append("'사용자의 결과 요약 내용', '홀랜드 유형 3개 추천', '추천 진로', '조언 및 계획' 로 구성해줘.");
        prompt.append("답변은 1200자를 초과하지 않도록 하고, 다정하고 따뜻한 어투로 작성해줘.");

        return prompt.toString();
    }


}

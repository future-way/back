package com.team.futureway.consult;

import com.team.futureway.consult.entity.Question;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class question {

    @Test
    void 질문번호증가() {
        // given
        Question question = new Question(null, 1L, 1, "Sample question", null, LocalDateTime.now());

        // when
        question.incrementQuestionNumber();

        // then
        assertEquals(2, question.getQuestionNumber());
    }

    @Test
    void 질문번호0에서증가() {
        // given
        Question question = new Question(null, 1L, 0, "Sample question", null, LocalDateTime.now());

        // when
        question.incrementQuestionNumber();

        // then
        assertEquals(1, question.getQuestionNumber()); // expect questionNumber to be 1
    }

    @Test
    void 답변설정() {
        // given
        Question question = new Question(null, 1L, 1, "Sample question", null, LocalDateTime.now());

        // when
        question.setAnswer("Sample answer");

        // then
        assertEquals("Sample answer", question.getAnswer());
    }

    @Test
    void 질문에서중괄호제거() {
        // given
        String inputMessage = "This is a {sample} message.";
        Question question = new Question(null, 1L, 1, inputMessage, null, LocalDateTime.now());

        // when
        question.removeBracesAndTextFromQuestion(inputMessage);

        // then
        assertEquals("This is a  message.", question.getQuestionMessage());
    }

    @Test
    void 질문에서중괄호제거_빈중괄호() {
        // given
        String inputMessage = "Message with empty braces {} in it.";
        Question question = new Question(null, 1L, 1, inputMessage, null, LocalDateTime.now());

        // when
        question.removeBracesAndTextFromQuestion(inputMessage);

        // then
        assertEquals("Message with empty braces  in it.", question.getQuestionMessage());
    }
}

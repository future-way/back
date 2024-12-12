package com.team.futureway.gemini.entity;

import com.team.futureway.gemini.dto.UserTypeDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user_type")
public class UserType {
    @Id
    @Column(name = "user_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userTypeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "question", nullable = false, length = 255)
    private String question;

    @Column(name = "select_type", length = 255)
    private String selectType;

    @Column(name = "answer", length = 255)
    private String answer;

    @Column(name = "user_type", length = 255)
    private String userType;

    public static UserType of(Long userTypeId, Long userId, String question, String selectType, String answer, String userType) {
        return new UserType(userTypeId, userId, question, selectType, answer, userType);
    }
}

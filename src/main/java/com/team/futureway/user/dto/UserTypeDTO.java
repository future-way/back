package com.team.futureway.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserTypeDTO {

    private Long userId;
    private String question;
    private String selectType;
    private String answer;
    private String userType;

    public static UserTypeDTO of(Long userId, String question, String selectType, String answer, String userType) {
        return new UserTypeDTO(userId, question, selectType, answer, userType);
    }
}


package com.team.futureway.gemini.response;

import com.team.futureway.gemini.dto.UserTypeDTO;

public record UserTypeResponse(
        Long userId,
        String question,
        String selectType,
        String answer,
        String userType
) {
    public static UserTypeResponse of(UserTypeDTO dto) {
        return new UserTypeResponse(dto.getUserId(), dto.getQuestion(), dto.getSelectType(), dto.getAnswer(), dto.getUserType());
    }
}
package com.team.futureway.user.response;

public record UserTypeRequest(Long userId, String question, String selectType, String answer, String userType) {
}

package com.team.futureway.gemini.response;

public record UserTypeRequest(Long userId, String question, String selectType, String answer, String userType) {
}

package com.team.futureway.common.exception;

public record ErrorResponse(ErrorCode errorCode, String message, Object payload) {
}
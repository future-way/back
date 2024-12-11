package com.team.futureway.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    USER_NOT_FOUND(ErrorCode.NOT_FOUND, "사용자를 찾을 수 없습니다.", LogLevel.INFO),
    CONSULTATION_HISTORY_NOT_FOUND(ErrorCode.NOT_FOUND, "상담 내역을 찾을 수 없습니다.", LogLevel.INFO)
    ;

    private final ErrorCode errorCode;
    private final String message;
    private final LogLevel logLevel;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}

package com.team.futureway.common.exception;


public class CoreException extends RuntimeException {
    private final ErrorType errorType;
    private final Object payload;

    public CoreException(ErrorType errorType, Object payload) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.payload = payload;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public Object getPayload() {
        return payload;
    }
}
package org.tictoc.tictoc.global.exception.common;

import lombok.Getter;
import org.tictoc.tictoc.global.exception.ErrorCode;

@Getter
public class TicTocException extends RuntimeException {
    private final ErrorCode errorCode;

    public TicTocException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TicTocException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
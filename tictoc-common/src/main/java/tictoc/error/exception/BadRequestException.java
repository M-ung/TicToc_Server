package tictoc.error.exception;

import tictoc.error.ErrorCode;

public class BadRequestException extends TicTocException {
    public BadRequestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
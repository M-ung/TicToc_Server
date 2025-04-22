package tictoc.error.exception;

import tictoc.error.ErrorCode;

public class BatchException extends TicTocException {
    public BatchException(final ErrorCode errorCode) {
        super(errorCode);
    }
    public BatchException(final ErrorCode errorCode, final Throwable cause) {
        super(errorCode, cause);
    }
}
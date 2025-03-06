package tictoc.error.exception;

import tictoc.error.ErrorCode;

public class LogFileWriteException extends TicTocException {
    public LogFileWriteException(final ErrorCode errorCode) {
        super(errorCode);
    }
    public LogFileWriteException(final ErrorCode errorCode, final Throwable cause) {
        super(errorCode, cause);
    }
}
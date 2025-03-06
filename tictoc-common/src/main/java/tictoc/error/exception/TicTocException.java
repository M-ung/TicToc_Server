package tictoc.error.exception;

import lombok.Getter;
import tictoc.error.ErrorCode;

@Getter
public class TicTocException extends RuntimeException {
    private final ErrorCode errorCode;

    public TicTocException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TicTocException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public TicTocException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public TicTocException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
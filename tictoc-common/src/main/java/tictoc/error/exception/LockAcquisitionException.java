package tictoc.error.exception;

import tictoc.error.ErrorCode;

public class LockAcquisitionException extends TicTocException {
    public LockAcquisitionException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
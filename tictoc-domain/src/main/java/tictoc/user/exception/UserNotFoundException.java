package tictoc.user.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class UserNotFoundException extends TicTocException {
    public UserNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
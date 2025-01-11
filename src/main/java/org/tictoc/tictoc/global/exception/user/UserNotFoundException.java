package org.tictoc.tictoc.global.exception.user;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;

public class UserNotFoundException extends TicTocException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
    public UserNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
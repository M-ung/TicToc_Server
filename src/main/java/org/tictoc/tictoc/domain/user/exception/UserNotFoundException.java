package org.tictoc.tictoc.domain.user.exception;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class UserNotFoundException extends TicTocException {
    public UserNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
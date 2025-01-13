package org.tictoc.tictoc.global.error.exception;


import org.tictoc.tictoc.global.error.ErrorCode;

public class UnauthorizedException extends TicTocException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED);
  }

  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
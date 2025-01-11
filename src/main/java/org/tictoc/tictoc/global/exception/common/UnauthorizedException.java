package org.tictoc.tictoc.global.exception.common;


import org.tictoc.tictoc.global.exception.ErrorCode;

public class UnauthorizedException extends TicTocException {
  public UnauthorizedException() {
    super(ErrorCode.UNAUTHORIZED);
  }

  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
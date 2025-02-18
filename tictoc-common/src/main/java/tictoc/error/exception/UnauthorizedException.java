package tictoc.error.exception;


import tictoc.error.ErrorCode;

public class UnauthorizedException extends TicTocException {
  public UnauthorizedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
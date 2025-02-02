package tictoc.bid.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class BidNoAccessException extends TicTocException {
  public BidNoAccessException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

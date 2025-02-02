package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class AuctionNoAccessException extends TicTocException {
  public AuctionNoAccessException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

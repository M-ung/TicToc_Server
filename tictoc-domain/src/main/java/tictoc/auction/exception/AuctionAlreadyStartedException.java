package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class AuctionAlreadyStartedException extends TicTocException {
  public AuctionAlreadyStartedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

package tictoc.bid.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class AuctionAlreadyBidException extends TicTocException {
  public AuctionAlreadyBidException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class ConflictAuctionUpdateException extends TicTocException {
  public ConflictAuctionUpdateException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class ConflictAuctionDeleteException extends TicTocException {
  public ConflictAuctionDeleteException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

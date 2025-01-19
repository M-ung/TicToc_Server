package org.tictoc.tictoc.domain.auction.exception.auction;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class ConflictAuctionDeleteException extends TicTocException {
  public ConflictAuctionDeleteException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

package org.tictoc.tictoc.domain.auction.exception.auction;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class ConflictAuctionUpdateException extends TicTocException {
  public ConflictAuctionUpdateException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

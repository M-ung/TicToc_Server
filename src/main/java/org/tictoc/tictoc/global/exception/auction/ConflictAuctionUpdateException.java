package org.tictoc.tictoc.global.exception.auction;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;

public class ConflictAuctionUpdateException extends TicTocException {
  public ConflictAuctionUpdateException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

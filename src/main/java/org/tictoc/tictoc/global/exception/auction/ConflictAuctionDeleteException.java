package org.tictoc.tictoc.global.exception.auction;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;

public class ConflictAuctionDeleteException extends TicTocException {
  public ConflictAuctionDeleteException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

package org.tictoc.tictoc.global.exception.auction;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;

public class AuctionAlreadyStartedException extends TicTocException {
  public AuctionAlreadyStartedException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

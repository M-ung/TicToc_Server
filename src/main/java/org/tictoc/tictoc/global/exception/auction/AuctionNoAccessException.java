package org.tictoc.tictoc.global.exception.auction;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;

public class AuctionNoAccessException extends TicTocException {
  public AuctionNoAccessException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

package org.tictoc.tictoc.domain.auction.exception;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class AuctionNoAccessException extends TicTocException {
  public AuctionNoAccessException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

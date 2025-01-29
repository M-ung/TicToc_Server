package org.tictoc.tictoc.domain.auction.exception.bid;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class BidNoAccessException extends TicTocException {
  public BidNoAccessException(final ErrorCode errorCode) {
    super(errorCode);
  }
}

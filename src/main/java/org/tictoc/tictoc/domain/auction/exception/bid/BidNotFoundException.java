package org.tictoc.tictoc.domain.auction.exception.bid;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class BidNotFoundException extends TicTocException {
    public BidNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
package org.tictoc.tictoc.global.exception.auction;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;

public class AuctionNotFoundException extends TicTocException {
    public AuctionNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
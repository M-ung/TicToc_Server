package org.tictoc.tictoc.domain.auction.exception.auction;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class InvalidAuctionTimesException extends TicTocException {
    public InvalidAuctionTimesException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
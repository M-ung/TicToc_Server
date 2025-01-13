package org.tictoc.tictoc.domain.auction.exception;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;


public class DuplicateAuctionDateException extends TicTocException {
    public DuplicateAuctionDateException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
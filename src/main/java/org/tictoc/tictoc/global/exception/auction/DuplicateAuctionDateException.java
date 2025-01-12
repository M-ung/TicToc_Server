package org.tictoc.tictoc.global.exception.auction;

import org.tictoc.tictoc.global.exception.ErrorCode;
import org.tictoc.tictoc.global.exception.common.TicTocException;


public class DuplicateAuctionDateException extends TicTocException {
    public DuplicateAuctionDateException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
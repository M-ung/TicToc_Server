package org.tictoc.tictoc.domain.auction.exception.location;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class AuctionLocationNotFoundException extends TicTocException {
    public AuctionLocationNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
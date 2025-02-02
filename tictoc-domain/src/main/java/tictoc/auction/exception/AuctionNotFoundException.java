package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class AuctionNotFoundException extends TicTocException {
    public AuctionNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
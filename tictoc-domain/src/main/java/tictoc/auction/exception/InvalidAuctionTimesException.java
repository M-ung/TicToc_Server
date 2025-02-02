package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class InvalidAuctionTimesException extends TicTocException {
    public InvalidAuctionTimesException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
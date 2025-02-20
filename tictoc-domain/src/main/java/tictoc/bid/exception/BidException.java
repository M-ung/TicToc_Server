package tictoc.bid.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class BidException extends TicTocException {
    public BidException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
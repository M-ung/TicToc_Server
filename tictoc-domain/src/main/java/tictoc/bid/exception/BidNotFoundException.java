package tictoc.bid.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class BidNotFoundException extends TicTocException {
    public BidNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
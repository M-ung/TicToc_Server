package tictoc.bid.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class InvalidBidPriceException extends TicTocException {
    public InvalidBidPriceException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
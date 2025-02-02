package tictoc.auction.exception.location;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class AuctionLocationNotFoundException extends TicTocException {
    public AuctionLocationNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
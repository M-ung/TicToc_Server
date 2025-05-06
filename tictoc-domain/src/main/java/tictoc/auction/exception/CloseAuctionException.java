package tictoc.auction.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class CloseAuctionException extends TicTocException {
    public CloseAuctionException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
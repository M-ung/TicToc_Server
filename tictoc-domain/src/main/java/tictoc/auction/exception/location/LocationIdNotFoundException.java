package tictoc.auction.exception.location;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class LocationIdNotFoundException extends TicTocException {
    public LocationIdNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
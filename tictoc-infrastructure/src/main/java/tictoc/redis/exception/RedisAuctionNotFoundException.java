package tictoc.redis.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class RedisAuctionNotFoundException extends TicTocException {
    public RedisAuctionNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
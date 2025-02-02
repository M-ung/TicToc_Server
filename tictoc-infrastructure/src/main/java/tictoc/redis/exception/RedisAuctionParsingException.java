package tictoc.redis.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class RedisAuctionParsingException extends TicTocException {
    public RedisAuctionParsingException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
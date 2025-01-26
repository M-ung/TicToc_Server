package org.tictoc.tictoc.infra.redis.exception;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class RedisAuctionParsingException extends TicTocException {
    public RedisAuctionParsingException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
package org.tictoc.tictoc.infra.redis.exception;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class RedisAuctionNotFoundException extends TicTocException {
    public RedisAuctionNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
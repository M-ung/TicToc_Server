package org.tictoc.tictoc.infra.kafka.exception;

import org.tictoc.tictoc.global.error.ErrorCode;
import org.tictoc.tictoc.global.error.exception.TicTocException;

public class KafkaAuctionCloseProcessingException extends TicTocException {
    public KafkaAuctionCloseProcessingException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
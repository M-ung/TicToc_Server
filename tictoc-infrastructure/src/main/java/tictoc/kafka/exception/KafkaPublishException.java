package tictoc.kafka.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class KafkaPublishException extends TicTocException {
    public KafkaPublishException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
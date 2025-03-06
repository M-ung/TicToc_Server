package tictoc.kafka.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class KafkaDeserializationException extends TicTocException {
    public KafkaDeserializationException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
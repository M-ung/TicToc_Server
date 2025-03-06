package tictoc.kafka.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class KafkaConsumeException extends TicTocException {
    public KafkaConsumeException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
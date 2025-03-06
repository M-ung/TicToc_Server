package tictoc.kafka.exception;

import tictoc.error.ErrorCode;
import tictoc.error.exception.TicTocException;

public class KafkaSettingException extends TicTocException {
    public KafkaSettingException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
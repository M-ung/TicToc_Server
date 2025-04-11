package tictoc.error.exception;

import tictoc.error.ErrorCode;

import java.io.IOException;

public class CsvFailException extends TicTocException {
    public CsvFailException(IOException e, final ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode, e);
    }
}
package org.tictoc.tictoc.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tictoc.tictoc.global.exception.common.TicTocException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TicTocException.class)
    public ResponseEntity<ErrorResponse> ticTocExceptionHandler(final TicTocException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ErrorResponse.of(e.getErrorCode()));
    }
}

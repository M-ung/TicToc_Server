package org.tictoc.tictoc.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"찾을 수 없는 회원입니다."),


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}

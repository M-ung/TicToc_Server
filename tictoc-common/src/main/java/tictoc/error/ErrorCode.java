package tictoc.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // User
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 회원입니다."),

    // Auction
    AUCTION_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 경매입니다."),
    AUCTION_NO_ACCESS(HttpStatus.FORBIDDEN,"경매에 대한 접근 권한이 없습니다."),
    AUCTION_ALREADY_STARTED(HttpStatus.FORBIDDEN,"이미 경매가 시작되었습니다."),
    DUPLICATE_AUCTION_DATE(HttpStatus.BAD_REQUEST,"중복된 경매 날짜가 있습니다."),
    CONFLICT_AUCTION_UPDATE(HttpStatus.BAD_REQUEST,"경매 수정이 충돌나서 할 수 없습니다."),
    CONFLICT_AUCTION_DELETE(HttpStatus.BAD_REQUEST,"경매 삭제가 충돌나서 할 수 없습니다."),
    INVALID_AUCTION_TIME_RANGE(HttpStatus.BAD_REQUEST,"sellStartTime은 sellEndTime보다 이전이어야 합니다."),

    // Bid
    AUCTION_ALREADY_FINISHED(HttpStatus.BAD_REQUEST, "이미 경매가 종료되었습니다."),
    BID_NO_ACCESS(HttpStatus.FORBIDDEN,"입찰에 대한 접근 권한이 없습니다."),
    INVALID_BID_PRICE(HttpStatus.BAD_REQUEST,"현재 경매가보다 낮은 입찰가를 입력했습니다."),
    BID_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 입찰입니다."),
    BID_FAIL(HttpStatus.BAD_REQUEST,"이미 입찰되었습니다."),

    // Redis Auction
    REDIS_AUCTION_NOT_FOUND(HttpStatus.BAD_REQUEST,"레디스에서 찾을 수 없는 경매입니다."),
    REDIS_AUCTION_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"레디스에서 경매 데이터를 파싱하는 데 실패했습니다."),

    // Kafka Auction
    KAFKA_AUCTION_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"카프카에서 경매 종료 진행을 실패했습니다."),

    // Location
    LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 지역 정보입니다."),
    AUCTION_LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 AuctionLocation 입니다."),

    // Server
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    UNSUPPORTED_TOKEN_TYPE(HttpStatus.UNAUTHORIZED,  "잘못된 토큰 형식입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED,  "잘못된 토큰 구조입니다."),
    INVALID_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰 서명입니다."),
    TOKEN_SUBJECT_NOT_NUMERIC_STRING(HttpStatus.UNAUTHORIZED, "토큰의 subject가 숫자 문자열이 아닙니다."),

    KAKAO_BAD_REQUEST(HttpStatus.BAD_REQUEST, "카카오 로그인 통신에 실패하였습니다.");

    private final HttpStatus status;
    private final String message;
}

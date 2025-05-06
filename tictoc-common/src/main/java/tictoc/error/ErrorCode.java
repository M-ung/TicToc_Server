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
    AUCTION_TIME_OVER(HttpStatus.BAD_REQUEST,"경매 시간이 종료되었습니다."),
    INVALID_AUCTION_TIME_RANGE(HttpStatus.BAD_REQUEST,"sellStartTime은 sellEndTime보다 이전이어야 합니다."),
    CLOSE_AUCTION_ERROR(HttpStatus.BAD_REQUEST,"경매 종료 실패입니다. (찾을 수 없는 사용거나 보유 금액이 적습니다.)"),

    // Bid
    AUCTION_ALREADY_FINISHED(HttpStatus.BAD_REQUEST, "이미 경매가 종료되었습니다."),
    BID_NO_ACCESS(HttpStatus.FORBIDDEN,"입찰에 대한 접근 권한이 없습니다."),
    INVALID_BID_PRICE(HttpStatus.BAD_REQUEST,"현재 경매가보다 낮은 입찰가를 입력했습니다."),
    INVALID_PROFILE_MONEY(HttpStatus.BAD_REQUEST,"현재 경매가보다 낮은 금액을 소유하고 있습니다."),
    BID_NOT_FOUND(HttpStatus.BAD_REQUEST,"찾을 수 없는 입찰입니다."),
    BID_FAIL(HttpStatus.BAD_REQUEST,"이미 입찰되었습니다."),

    // Redis
    REDIS_AUCTION_NOT_FOUND(HttpStatus.BAD_REQUEST,"레디스에서 찾을 수 없는 경매입니다."),
    REDIS_AUCTION_PARSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"레디스에서 경매 데이터를 파싱하는 데 실패했습니다."),

    // Kafka
    KAFKA_PUBLISH_ERROR(HttpStatus.BAD_REQUEST,"카프카에서 발행에 실패했습니다."),
    KAFKA_CONSUME_ERROR(HttpStatus.BAD_REQUEST,"카프카에서 소비에 실패했습니다."),
    KAFKA_SETTING_ERROR(HttpStatus.BAD_REQUEST,"카프카에서 세팅에 실패했습니다."),
    KAFKA_DESERIALIZATION(HttpStatus.BAD_REQUEST,"카프카에서 역직렬화에 실패했습니다."),

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

    KAKAO_BAD_REQUEST(HttpStatus.BAD_REQUEST, "카카오 로그인 통신에 실패하였습니다."),

    // Batch
    BATCH_ERROR(HttpStatus.BAD_REQUEST,"배치 작업 중 오류 발생했습니다."),

    // Log
    LOG_FILE_WRITE_ERROR(HttpStatus.BAD_REQUEST,"로그 파일 저장 중 오류 발생했습니다."),
    LOG_DIRECTORY_CREATION_ERROR(HttpStatus.BAD_REQUEST,"로그 파일 디렉토리 생성 중 오류 발생했습니다."),
    LOG_FILE_CREATION_ERROR(HttpStatus.BAD_REQUEST,"로그 파일 생성 중 오류 발생했습니다."),
    CSV_FAIL(HttpStatus.BAD_REQUEST,"사용자 로그인 기록 CSV 저장 중 오류 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}

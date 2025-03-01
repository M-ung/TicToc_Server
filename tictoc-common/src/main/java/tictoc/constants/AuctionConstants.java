package tictoc.constants;

public class AuctionConstants {
    public static final String AUCTION_CLOSE_KEY_PREFIX = "auction:close:";
    public static final String EXPIRED_STATUS = "EXPIRED";
    public static final String REDIS_KEY_EVENT_EXPIRED = "__keyevent@0__:expired";
    public static final String REDIS_KEY_SPACE_EVENT_PARAM = "notify-keyspace-events";
    public static final String REDIS_KEY_SPACE_EVENT_VALUE = "Ex";
}

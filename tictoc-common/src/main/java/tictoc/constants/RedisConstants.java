package tictoc.constants;

public class RedisConstants {
    public static final String AUCTION_CLOSE_KEY_PREFIX = "auction:close:";
    public static final String EXPIRED_STATUS = "EXPIRED";
    public static final String REDIS_KEY_EVENT_EXPIRED = "__keyevent@0__:expired";
    public static final String REDIS_KEY_SPACE_EVENT_PARAM = "notify-keyspace-events";
    public static final String REDIS_KEY_SPACE_EVENT_VALUE = "Ex";

    public static final String LOCK_PREFIX = "bid:";
    public static final double LOCK_WAIT_TIME = 0;
    public static final long LOCK_LEASE_TIME = 3;
}

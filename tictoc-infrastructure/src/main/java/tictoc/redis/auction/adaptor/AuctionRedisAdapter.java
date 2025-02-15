package tictoc.redis.auction.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tictoc.redis.auction.model.AuctionCloseEvent;
import tictoc.redis.auction.port.out.AuctionRedisPort;
import tictoc.redis.constants.AuctionRedisConstants;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuctionRedisAdapter implements AuctionRedisPort {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String auctionCloseKey = AuctionRedisConstants.AUCTION_CLOSE_KEY;
    private final String auctionCloseChannel = AuctionRedisConstants.AUCTION_CLOSE_CHANNEL;

    @Override
    public void saveAuctionClose(Long auctionId, LocalDateTime sellEndTime) {
        long auctionCloseMillis = sellEndTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisTemplate.opsForZSet().add(auctionCloseKey, auctionId, auctionCloseMillis);
        Date expirationTime = Date.from(sellEndTime.atZone(ZoneId.systemDefault()).toInstant());
        redisTemplate.expireAt(auctionCloseKey, expirationTime);
        publishAuctionCloseEvent(auctionId, sellEndTime);
    }

    private void publishAuctionCloseEvent(Long auctionId, LocalDateTime sellEndTime) {
        long auctionCloseMillis = sellEndTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisTemplate.convertAndSend(auctionCloseChannel, AuctionCloseEvent.of(auctionId, auctionCloseMillis));
    }

    @Override
    public Long getAuctionClose(Long auctionId) {
        Double auctionCloseMillis = redisTemplate.opsForZSet().score(auctionCloseKey, auctionId);
        return auctionCloseMillis != null ? auctionCloseMillis.longValue() : null;
    }

    @Override
    public void deleteAuctionClose(Long auctionId) {
        redisTemplate.opsForZSet().remove(auctionCloseKey, auctionId);
    }
}
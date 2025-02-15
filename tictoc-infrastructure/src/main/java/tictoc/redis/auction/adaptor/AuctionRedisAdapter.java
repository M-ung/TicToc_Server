package tictoc.redis.auction.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tictoc.redis.auction.port.out.AuctionRedisPort;
import tictoc.redis.constants.AuctionRedisConstants;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class AuctionRedisAdapter implements AuctionRedisPort {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String auctionCloseKey = AuctionRedisConstants.AUCTION_CLOSE_KEY;

    @Override
    public void saveAuctionClose(Long auctionId, LocalDateTime sellEndTime) {
        long closeTimeMillis = sellEndTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisTemplate.opsForZSet().add(auctionCloseKey, auctionId, closeTimeMillis);
    }

    @Override
    public void updateAuctionClose(Long auctionId, LocalDateTime sellEndTime) {
        long closeTimeMillis = sellEndTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        redisTemplate.opsForZSet().remove(auctionCloseKey, auctionId);
        redisTemplate.opsForZSet().add(auctionCloseKey, auctionId, closeTimeMillis);
    }

    @Override
    public Long getAuctionClose(Long auctionId) {
        Double sellEndTimeMillis = redisTemplate.opsForZSet().score(auctionCloseKey, auctionId);
        return sellEndTimeMillis != null ? sellEndTimeMillis.longValue() : null;
    }

    @Override
    public void deleteAuctionClose(Long auctionId) {
        redisTemplate.opsForZSet().remove(auctionCloseKey, auctionId);
    }
}
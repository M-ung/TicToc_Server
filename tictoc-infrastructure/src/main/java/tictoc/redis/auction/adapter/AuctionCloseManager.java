package tictoc.redis.auction.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import tictoc.annotation.Adapter;
import tictoc.constants.RedisConstants;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Adapter
@RequiredArgsConstructor
public class AuctionCloseManager implements CloseAuctionUseCase {
    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(final Long auctionId, LocalDateTime auctionCloseTime) {
        long secondsUntilExpire = Duration.between(LocalDateTime.now(), auctionCloseTime).getSeconds();
        String key = RedisConstants.AUCTION_CLOSE_KEY_PREFIX + auctionId;
        redisTemplate.opsForValue().set(key, RedisConstants.EXPIRED_STATUS, secondsUntilExpire, TimeUnit.SECONDS);
    }

    @Override
    public void delete(final Long auctionId) {
        redisTemplate.delete(RedisConstants.AUCTION_CLOSE_KEY_PREFIX + auctionId);
    }
}
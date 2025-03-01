package tictoc.redis.auction.adaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import tictoc.redis.auction.port.out.AuctionRedisPort;
import tictoc.redis.constants.AuctionRedisConstants;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class AuctionRedisAdapter implements AuctionRedisPort {
    private final StringRedisTemplate redisTemplate;
    private final String auctionCloseKey = AuctionRedisConstants.AUCTION_CLOSE_KEY;

    @Override
    public void save(final Long auctionId, LocalDateTime auctionCloseTime) {
        long closedTime = auctionCloseTime.toEpochSecond(ZoneOffset.UTC);
        redisTemplate.opsForZSet().add(auctionCloseKey, auctionId.toString(), closedTime);
    }

    @Override
    public void delete(final Long auctionId) {
        redisTemplate.opsForZSet().remove(auctionCloseKey, auctionId);
    }
}
package tictoc.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RedisAuctionServiceImpl implements RedisAuctionService {
//    private final StringRedisTemplate redisTemplate;
//    private final ObjectMapper objectMapper;
//    private static final String KEY = "AuctionClose";
//    private static final String FIELD_PREFIX = "auction:close:";
//
//    @Override
//    public void save(RedisAuctionMessageDTO.auctionClose messageDTO) throws JsonProcessingException {
//        var field = FIELD_PREFIX + messageDTO.auctionId();
//        var auctionJson = objectMapper.writeValueAsString(messageDTO.auction());
//        redisTemplate.opsForHash().put(KEY, field, auctionJson);
//    }
//
//    @Override
//    public boolean exists(Long auctionId) {
//        var field = FIELD_PREFIX + auctionId;
//        return redisTemplate.opsForHash().hasKey(KEY, field);
//    }
//
//    @Override
//    public Auction find(Long auctionId) {
//        var field = FIELD_PREFIX + auctionId;
//        var json = redisTemplate.opsForHash().get(KEY, field);
//        if (json == null) {
//            throw new RedisAuctionNotFoundException(REDIS_AUCTION_NOT_FOUND);
//        }
//        return parse(json.toString());
//    }
//
//    @Override
//    public void delete(Long auctionId) {
//        var field = FIELD_PREFIX + auctionId;
//        redisTemplate.opsForHash().delete(KEY, field);
//    }
//
//    private Auction parse(String json) {
//        try {
//            return objectMapper.readValue(json, Auction.class);
//        } catch (JsonProcessingException e) {
//            throw new RedisAuctionParsingException(REDIS_AUCTION_PARSING_ERROR);
//        }
//    }
}
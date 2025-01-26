package org.tictoc.tictoc.infra.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.infra.redis.dto.RedisAuctionMessageDTO;
import org.tictoc.tictoc.infra.redis.exception.RedisAuctionNotFoundException;
import org.tictoc.tictoc.infra.redis.exception.RedisAuctionParsingException;
import static org.tictoc.tictoc.global.error.ErrorCode.REDIS_AUCTION_NOT_FOUND;
import static org.tictoc.tictoc.global.error.ErrorCode.REDIS_AUCTION_PARSING_ERROR;

@Service
@RequiredArgsConstructor
public class RedisAuctionServiceImpl implements RedisAuctionService {
    private final StringRedisTemplate redisTemplate;
    private static final String KEY = "AuctionClose";
    private static final String FIELD_PREFIX = "auction:close:";

    @Override
    public void save(RedisAuctionMessageDTO.auctionClose messageDTO) throws JsonProcessingException {
        var field = FIELD_PREFIX + messageDTO.auctionId();
        var auctionJson = new ObjectMapper().writeValueAsString(messageDTO.auction());
        redisTemplate.opsForHash().put(KEY, field, auctionJson);
    }

    @Override
    public boolean exists(Long auctionId) {
        var field = FIELD_PREFIX + auctionId;
        return redisTemplate.opsForHash().hasKey(KEY, field);
    }

    @Override
    public Auction find(Long auctionId) {
        var field = FIELD_PREFIX + auctionId;
        var json = redisTemplate.opsForHash().get(KEY, field);
        if (json == null) {
            throw new RedisAuctionNotFoundException(REDIS_AUCTION_NOT_FOUND);
        }
        return parse(json.toString());
    }

    @Override
    public void delete(Long auctionId) {
        var field = FIELD_PREFIX + auctionId;
        redisTemplate.opsForHash().delete(KEY, field);
    }

    private Auction parse(String json) {
        try {
            return new ObjectMapper().readValue(json, Auction.class);
        } catch (JsonProcessingException e) {
            throw new RedisAuctionParsingException(REDIS_AUCTION_PARSING_ERROR);
        }
    }
}
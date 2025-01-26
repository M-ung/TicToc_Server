package org.tictoc.tictoc.infra.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.infra.redis.dto.RedisAuctionMessageDTO;

public interface RedisAuctionService {
    void save(RedisAuctionMessageDTO.auctionClose messageDTO) throws JsonProcessingException;
    boolean exists(Long auctionId);
    Auction find(Long auctionId);
    void delete(Long auctionId);
}

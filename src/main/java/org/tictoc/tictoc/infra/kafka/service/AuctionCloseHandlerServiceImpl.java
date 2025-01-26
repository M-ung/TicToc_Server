package org.tictoc.tictoc.infra.kafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionNotFoundException;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;
import org.tictoc.tictoc.infra.redis.service.RedisAuctionService;
import static org.tictoc.tictoc.global.error.ErrorCode.AUCTION_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuctionCloseHandlerServiceImpl implements AuctionCloseHandlerService {

    private final AuctionRepository auctionRepository;
    private final RedisAuctionService redisAuctionService;

    @Override
    public void process(KafkaAuctionMessageDTO.auctionClose message) {
        var auctionId = message.auctionId();

        if (redisAuctionService.exists(auctionId)) {
            handleFromRedis(auctionId);
        } else {
            handleFromDB(auctionId);
        }
    }

    private void handleFromRedis(Long auctionId) {
        var auction = redisAuctionService.find(auctionId);
        auction.close();
        redisAuctionService.delete(auctionId);
        auctionRepository.save(auction);
    }

    private void handleFromDB(Long auctionId) {
        var auction = findAuctionById(auctionId);
        auction.close();
        auctionRepository.save(auction);
    }

    private Auction findAuctionById(final Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }
}
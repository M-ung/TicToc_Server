package org.tictoc.tictoc.infra.kafka.consumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.bid.WinningBid;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.BidRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.WinningBidRepository;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;
import org.tictoc.tictoc.infra.redis.service.RedisAuctionService;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionCloseHandlerServiceImpl implements AuctionCloseHandlerService {
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final WinningBidRepository winningBidRepository;
    private final RedisAuctionService redisAuctionService;

    @Override
    public void process(KafkaAuctionMessageDTO.AuctionClose message) {
        var auctionId = message.auctionId();
        var isInRedis = redisAuctionService.exists(auctionId);
        var auction = isInRedis ? redisAuctionService.find(auctionId) : auctionRepository.findByIdOrThrow(auctionId);
        close(auction, isInRedis);
    }

    private void close(Auction auction, boolean isInRedis) {
        if(auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
            auction.bid();
            var bid = bidRepository.findByAuctionIdAndStatusOrThrow(auction.getId());
            bid.win();
            winningBidRepository.save(WinningBid.of(auction, bid));
        } else {
            auction.notBid();
        }
        if (isInRedis) redisAuctionService.delete(auction.getId());
        auctionRepository.save(auction);
    }
}
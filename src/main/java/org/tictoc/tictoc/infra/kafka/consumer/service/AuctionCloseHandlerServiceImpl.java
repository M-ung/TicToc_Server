package org.tictoc.tictoc.infra.kafka.consumer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;
import org.tictoc.tictoc.domain.auction.entity.bid.WinningBid;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.BidRepository;
import org.tictoc.tictoc.domain.auction.repository.bid.WinningBidRepository;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;
import org.tictoc.tictoc.infra.redis.service.RedisAuctionService;

@Service
@RequiredArgsConstructor
public class AuctionCloseHandlerServiceImpl implements AuctionCloseHandlerService {
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final WinningBidRepository winningBidRepository;
    private final RedisAuctionService redisAuctionService;

    @Override
    public void process(KafkaAuctionMessageDTO.AuctionClose message) {
        var auctionId = message.auctionId();
        var checkRedis = redisAuctionService.exists(auctionId);
        var findAuction = checkRedis ? redisAuctionService.find(auctionId) : auctionRepository.findByIdOrThrow(auctionId);
        closeAuction(findAuction, checkRedis);
    }

    private void closeAuction(Auction auction, boolean isInRedis) {
        auction.close();
        if (isInRedis) redisAuctionService.delete(auction.getId());
        auctionRepository.save(auction);
        winningBidRepository.save(WinningBid.of(auction, determineWinningBid(auction.getId())));
    }

    private Bid determineWinningBid(Long auctionId) {
        var bid = bidRepository.findByAuctionIdAndStatusOrThrow(auctionId);
        bid.win();
        return bid;
    }
}
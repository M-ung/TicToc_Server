package org.tictoc.tictoc.domain.auction.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionProgressScheduler {

    private final AuctionRepository auctionRepository;

    @Scheduled(fixedRate = 60000) // 1분 간격으로 실행
    @Transactional
    public void updateAuctionProgress() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> finishedAuctions = auctionRepository.findByProgressNotAndAuctionCloseTime(AuctionProgress.BID, now);

        finishedAuctions.forEach(auction -> {
            if (auction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
                auction.notBid();
            } else if (auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
                auction.bid();
            }
        });
    }
}
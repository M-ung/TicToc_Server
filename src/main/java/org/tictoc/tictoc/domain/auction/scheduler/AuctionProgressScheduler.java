package org.tictoc.tictoc.domain.auction.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.repository.AuctionRepository;
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

        List<Auction> finishedAuctions = auctionRepository.findByProgressNotAndAuctionCloseTime(AuctionProgress.FINISHED, now);

        finishedAuctions.forEach(Auction::finished);

        //TODO 입찰될 수 있게 해야 함.
    }
}
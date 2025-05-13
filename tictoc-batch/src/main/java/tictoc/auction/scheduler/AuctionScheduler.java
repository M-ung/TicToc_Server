package tictoc.auction.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.model.Bid;
import tictoc.bid.model.WinningBid;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.bid.port.WinningBidRepositoryPort;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import tictoc.user.model.UserSchedule;
import tictoc.user.port.UserScheduleRepositoryPort;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuctionScheduler {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;
    private final WinningBidRepositoryPort winningBidRepositoryPort;
    private final UserScheduleRepositoryPort userScheduleRepositoryPort;
    private final CloseAuctionUseCase closeAuctionUseCase;

    @Scheduled(cron = "0 0 */6 * * *")
    public void close() {
        LocalDateTime now = LocalDateTime.now();
        auctionRepositoryPort.findExpiredAuctions(
                List.of(AuctionProgress.NOT_STARTED, AuctionProgress.IN_PROGRESS), now
        ).forEach(auction -> {
            closeExpiredAuction(auction);
            closeAuctionUseCase.delete(auction.getId());
        });
    }

    private void closeExpiredAuction(Auction findAuction) {
        if (findAuction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
            findAuction.notBid();
        } else {
            findAuction.bid();
            Bid findBid = bidRepositoryPort.findBidByAuctionId(findAuction.getId());
            processWinningBid(findAuction, findBid);
            processUserSchedule(findAuction, findBid);
        }
        auctionRepositoryPort.save(findAuction);
    }

    private void processWinningBid(Auction auction, Bid bid) {
        bid.win();
        bidRepositoryPort.save(bid);
        winningBidRepositoryPort.save(WinningBid.of(auction, bid));
    }

    private void processUserSchedule(Auction auction, Bid bid) {
        userScheduleRepositoryPort.save(UserSchedule.of(auction, bid));
    }
}
package tictoc.auction.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import tictoc.annotation.Event;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.model.Bid;
import tictoc.bid.model.WinningBid;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.bid.port.WinningBidRepositoryPort;
import tictoc.constants.RedisConstants;
import tictoc.profile.port.ProfileRepositoryPort;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import tictoc.user.model.UserSchedule;
import tictoc.user.port.UserScheduleRepositoryPort;
import java.nio.charset.StandardCharsets;

@Event("Listener")
@RequiredArgsConstructor
public class CloseAuctionEventListener implements MessageListener {
    private final ProfileRepositoryPort profileRepositoryPort;
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;
    private final WinningBidRepositoryPort winningBidRepositoryPort;
    private final UserScheduleRepositoryPort userScheduleRepositoryPort;
    private final CloseAuctionUseCase closeAuctionUseCase;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        if (expiredKey.startsWith(RedisConstants.AUCTION_CLOSE_KEY_PREFIX)) {
            Long auctionId = parseAuctionId(expiredKey);
            Auction auction = auctionRepositoryPort.findAuctionById(auctionId);
            closeAuction(auction);
            closeAuctionUseCase.delete(auctionId);
        }
    }

    private Long parseAuctionId(String expiredKey) {
        return Long.parseLong(expiredKey.replace(RedisConstants.AUCTION_CLOSE_KEY_PREFIX, ""));
    }

    private void closeAuction(Auction auction) {
        if (auction.getProgress() == AuctionProgress.NOT_STARTED) {
            auction.notBid();
        } else {
            auction.bid();
            Bid bid = bidRepositoryPort.findBidByAuctionId(auction.getId());
            processWinningBid(auction, bid);
            processUserSchedule(auction, bid);
        }
        auctionRepositoryPort.save(auction);
    }

    private void processWinningBid(Auction auction, Bid bid) {
        bid.win();
        bidRepositoryPort.save(bid);
        winningBidRepositoryPort.save(WinningBid.of(auction, bid));
        profileRepositoryPort.subtractMoney(bid.getBidderId(), bid.getBidPrice());
        profileRepositoryPort.addMoney(auction.getAuctioneerId(), bid.getBidPrice());
    }

    private void processUserSchedule(Auction auction, Bid bid) {
        userScheduleRepositoryPort.save(UserSchedule.of(auction, bid));
    }
}
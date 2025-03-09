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
import tictoc.constants.AuctionConstants;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import tictoc.user.model.UserSchedule;
import tictoc.user.port.UserScheduleRepositoryPort;
import java.nio.charset.StandardCharsets;

@Event("Listener")
@RequiredArgsConstructor
public class CloseAuctionEventListener implements MessageListener {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;
    private final WinningBidRepositoryPort winningBidRepositoryPort;
    private final UserScheduleRepositoryPort userScheduleRepositoryPort;
    private final CloseAuctionUseCase closeAuctionUseCase;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        if (expiredKey.startsWith(AuctionConstants.AUCTION_CLOSE_KEY_PREFIX)) {
            Long auctionId = Long.parseLong(expiredKey.replace(AuctionConstants.AUCTION_CLOSE_KEY_PREFIX, ""));
            Auction findAuction = auctionRepositoryPort.findAuctionById(auctionId);
            close(findAuction);
            closeAuctionUseCase.delete(auctionId);
        }
    }

    private void close(Auction findAuction) {
        if (findAuction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
            findAuction.notBid();
        } else {
            findAuction.bid();
            Bid findBid = bidRepositoryPort.findBidByAuctionId(findAuction.getId());
            processWinningBid(findAuction, findBid);
            processUserSchedule(findAuction, findBid);
        }
        auctionRepositoryPort.saveAuction(findAuction);
    }

    private void processWinningBid(Auction auction, Bid bid) {
        bid.win();
        bidRepositoryPort.saveBid(bid);
        winningBidRepositoryPort.saveWinningBid(WinningBid.of(auction, bid));
    }

    private void processUserSchedule(Auction auction, Bid bid) {
        userScheduleRepositoryPort.saveUserSchedule(UserSchedule.of(auction, bid));
    }
}
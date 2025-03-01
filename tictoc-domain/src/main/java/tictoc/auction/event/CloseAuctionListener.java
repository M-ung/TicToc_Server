package tictoc.auction.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.bid.model.Bid;
import tictoc.bid.port.BidRepositoryPort;
import tictoc.constants.AuctionConstants;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CloseAuctionListener implements MessageListener {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final BidRepositoryPort bidRepositoryPort;
    private final CloseAuctionUseCase closeAuctionUseCase;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        if (expiredKey.startsWith(AuctionConstants.AUCTION_CLOSE_KEY_PREFIX)) {
            Long auctionId = Long.parseLong(expiredKey.replace(AuctionConstants.AUCTION_CLOSE_KEY_PREFIX, ""));
            Auction findAuction = auctionRepositoryPort.findAuctionById(auctionId);
            closeAndBid(findAuction);
            closeAuctionUseCase.delete(auctionId);
        }
    }

    private void closeAndBid(Auction findAuction) {
        if (findAuction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
            findAuction.notBid();
        } else {
            findAuction.bid();
            Bid findBid = bidRepositoryPort.findBidByAuctionId(findAuction.getId());
            findBid.win();
            bidRepositoryPort.saveBid(findBid);
        }
        auctionRepositoryPort.saveAuction(findAuction);
    }
}
package tictoc.auction.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.port.AuctionRepositoryPort;
import tictoc.constants.AuctionConstants;
import tictoc.redis.auction.port.out.CloseAuctionUseCase;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CloseAuctionListener implements MessageListener {
    private final AuctionRepositoryPort auctionRepositoryPort;
    private final CloseAuctionUseCase closeAuctionUseCase;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody(), StandardCharsets.UTF_8);
        if (expiredKey.startsWith(AuctionConstants.AUCTION_CLOSE_KEY_PREFIX)) {
            Long auctionId = Long.parseLong(expiredKey.replace(AuctionConstants.AUCTION_CLOSE_KEY_PREFIX, ""));
            Auction findAuction = auctionRepositoryPort.findAuctionByIdOrThrow(auctionId);
            close(findAuction);
            closeAuctionUseCase.delete(auctionId);
        }
    }

    private void close(Auction findAuction) {
        if (findAuction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
            findAuction.notBid();
        } else {
            findAuction.bid();
        }
        auctionRepositoryPort.saveAuction(findAuction);
    }
}
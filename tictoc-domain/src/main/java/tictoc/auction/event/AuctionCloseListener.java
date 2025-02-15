package tictoc.auction.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.auction.model.Auction;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.port.out.AuctionRepositoryPort;

@Component
@RequiredArgsConstructor
public class AuctionCloseListener {
    private final AuctionRepositoryPort auctionRepositoryPort;

    public void handleAuctionCloseEvent(String message) {
        String[] parts = message.split(":");
        if (parts.length == 2) {
            Long auctionId = Long.parseLong(parts[1]);
            closeAuction(auctionId);
        }
    }

    private void closeAuction(Long auctionId) {
        Auction auction = auctionRepositoryPort.findAuctionByIdOrThrow(auctionId);
        if(auction.getProgress().equals(AuctionProgress.NOT_STARTED)) {
            auction.notBid();
        } else {
            auction.bid();
        }
        auctionRepositoryPort.saveAuction(auction);
    }
}
package tictoc.auction.port.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tictoc.auction.exception.AuctionNotFoundException;
import tictoc.auction.exception.DuplicateAuctionDateException;
import tictoc.auction.model.Auction;
import tictoc.auction.repository.AuctionRepository;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import static tictoc.error.ErrorCode.AUCTION_NOT_FOUND;
import static tictoc.error.ErrorCode.DUPLICATE_AUCTION_DATE;

@Component
@RequiredArgsConstructor
public class AuctionAdaptor {
    private final AuctionRepository auctionRepository;

    public Auction saveAuctionEntity(Auction auction) {
        return auctionRepository.save(auction);
    }
    public Auction findAuctionByIdOrThrow(Long auctionId) {
        return auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }

    public void validateAuctionTimeRange(Long userId, LocalDateTime sellStartTime, LocalDateTime sellEndTime) {
        if (auctionRepository.existsAuctionInTimeRange(userId, sellStartTime, sellEndTime, TicTocStatus.ACTIVE)) {
            throw new DuplicateAuctionDateException(DUPLICATE_AUCTION_DATE);
        }
    }

    public List<Auction> findByProgressNotAndAuctionCloseTime(AuctionProgress progress, LocalDateTime auctionCloseTime) {
        return auctionRepository.findByProgressNotAndAuctionCloseTime(progress, auctionCloseTime);
    }
}

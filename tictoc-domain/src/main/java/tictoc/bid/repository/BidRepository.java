package tictoc.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.bid.model.Bid;
import tictoc.bid.exception.BidNotFoundException;
import tictoc.bid.model.type.BidStatus;
import java.util.Optional;
import static tictoc.error.ErrorCode.BID_NOT_FOUND;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
    Optional<Bid> findByAuctionIdAndStatus(final Long auctionId, BidStatus status);

    default Bid findByAuctionIdAndStatusOrThrow(final Long auctionId) {
        return findByAuctionIdAndStatus(auctionId, BidStatus.PROGRESS).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND));
    }
}
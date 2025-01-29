package org.tictoc.tictoc.domain.auction.repository.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.bid.Bid;
import org.tictoc.tictoc.domain.auction.entity.type.BidStatus;
import org.tictoc.tictoc.domain.auction.exception.bid.BidNotFoundException;
import java.util.Optional;
import static org.tictoc.tictoc.global.error.ErrorCode.BID_NOT_FOUND;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
    Optional<Bid> findByAuctionIdAndStatus(final Long auctionId, BidStatus status);

    default Bid findByAuctionIdAndStatusOrThrow(final Long auctionId, BidStatus status) {
        return findByAuctionIdAndStatus(auctionId, status).orElseThrow(() -> new BidNotFoundException(BID_NOT_FOUND));
    }
}
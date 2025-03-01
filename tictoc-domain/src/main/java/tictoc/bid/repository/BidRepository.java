package tictoc.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tictoc.bid.model.Bid;
import tictoc.bid.model.type.BidStatus;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long>, BidRepositoryCustom {
    Optional<Bid> findByAuctionIdAndStatus(final Long auctionId, BidStatus status);
}
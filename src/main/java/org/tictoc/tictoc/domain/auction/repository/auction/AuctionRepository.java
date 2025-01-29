package org.tictoc.tictoc.domain.auction.repository.auction;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionNotFoundException;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import java.time.LocalDateTime;
import java.util.List;
import static org.tictoc.tictoc.global.error.ErrorCode.AUCTION_NOT_FOUND;

public interface AuctionRepository extends JpaRepository<Auction, Long>, AuctionRepositoryCustom {
    @Query("SELECT COUNT(a) > 0 FROM Auction a " +
            "WHERE a.auctioneerId = :userId " +
            "AND a.status = :status " +
            "AND a.sellStartTime < :sellEndTime " +
            "AND a.sellEndTime > :sellStartTime")
    boolean existsAuctionInTimeRange(@Param("userId") Long userId, @Param("sellStartTime") LocalDateTime sellStartTime, @Param("sellEndTime") LocalDateTime sellEndTime, @Param("status") TicTocStatus status);
    List<Auction> findByProgressNotAndAuctionCloseTime(AuctionProgress progress, LocalDateTime auctionCloseTime);

    default Auction findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }
}
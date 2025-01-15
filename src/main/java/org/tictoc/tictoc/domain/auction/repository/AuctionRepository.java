package org.tictoc.tictoc.domain.auction.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tictoc.tictoc.domain.auction.entity.Auction;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query("SELECT COUNT(a) > 0 FROM Auction a " +
            "WHERE a.auctioneerId = :userId " +
            "AND a.sellStartTime <= :sellEndTime " +
            "AND a.sellEndTime >= :sellStartTime " +
            "AND a.status = org.tictoc.tictoc.global.common.entity.type.TicTocStatus.ACTIVE")
    boolean existsAuctionInTimeRange(@Param("userId") Long userId,
                                     @Param("sellStartTime") LocalDateTime sellStartTime,
                                     @Param("sellEndTime") LocalDateTime sellEndTime);
    List<Auction> findByProgressNotAndAuctionCloseTime(AuctionProgress progress, LocalDateTime auctionCloseTime);
}
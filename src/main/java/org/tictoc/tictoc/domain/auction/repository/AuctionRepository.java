package org.tictoc.tictoc.domain.auction.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tictoc.tictoc.domain.auction.entity.Auction;

import java.time.LocalDateTime;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    @Query("SELECT COUNT(a) > 0 FROM Auction a " +
            "WHERE a.auctioneerId = :userId " +
            "AND a.sellStartTime <= :sellEndTime " +
            "AND a.sellEndTime >= :sellStartTime")
    boolean existAuctionInTimeRange(@Param("userId") Long userId,
                                    @Param("startTime") LocalDateTime sellStartTime,
                                    @Param("endTime") LocalDateTime sellEndTime);
}

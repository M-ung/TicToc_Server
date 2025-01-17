package org.tictoc.tictoc.domain.auction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionResponseDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Auctions {
        private Long auctionId;
        private String title;
        private Integer startPrice;
        private Integer currentPrice;
        private LocalDateTime sellStartTime;
        private LocalDateTime sellEndTime;
        private LocalDateTime auctionOpenTime;
        private LocalDateTime auctionCloseTime;
        private AuctionProgress progress;
        private AuctionType type;
        private List<AuctionResponseDTO.Location> locations;
        public Auctions(Long auctionId, String title, Integer startPrice, Integer currentPrice, LocalDateTime sellStartTime, LocalDateTime sellEndTime, LocalDateTime auctionOpenTime, LocalDateTime auctionCloseTime, AuctionProgress progress, AuctionType type) {
            this.auctionId = auctionId;
            this.title = title;
            this.startPrice = startPrice;
            this.currentPrice = currentPrice;
            this.sellStartTime = sellStartTime;
            this.sellEndTime = sellEndTime;
            this.auctionOpenTime = auctionOpenTime;
            this.auctionCloseTime = auctionCloseTime;
            this.progress = progress;
            this.type = type;
        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Location {
        private Long auctionId;
        private String region;
        private String city;
        private String district;
        private String subDistrict;
    }
}
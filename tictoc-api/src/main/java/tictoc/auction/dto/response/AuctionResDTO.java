package tictoc.auction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;

import java.time.LocalDateTime;
import java.util.List;

public class AuctionResDTO {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Auction {
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
        private List<AuctionResDTO.Location> locations;
        public Auction(Long auctionId, String title, Integer startPrice, Integer currentPrice, LocalDateTime sellStartTime, LocalDateTime sellEndTime, LocalDateTime auctionOpenTime, LocalDateTime auctionCloseTime, AuctionProgress progress, AuctionType type) {
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
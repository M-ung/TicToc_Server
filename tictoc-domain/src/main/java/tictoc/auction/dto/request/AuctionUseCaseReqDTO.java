package tictoc.auction.dto.request;

import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;
import java.time.LocalDateTime;
import java.util.List;

public class AuctionUseCaseReqDTO {
    public record Register(
            String title,
            String content,
            Integer startPrice,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime,
            LocalDateTime auctionCloseTime,
            List<Location> locations,
            AuctionType type
    ) {}

    public record Update(
            String title,
            String content,
            Integer startPrice,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime,
            LocalDateTime auctionCloseTime,
            List<Location> locations,
            AuctionType type
    ) {}

    public record Filter(
            Integer startPrice,
            Integer endPrice,
            LocalDateTime sellStartTime,
            LocalDateTime sellEndTime,
            List<Location> locations,
            AuctionProgress progress,
            AuctionType type
    ) {}

    public record Location(
            String region,
            String city,
            String district,
            String subDistrict
    ) {}
}

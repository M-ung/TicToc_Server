package tictoc.redis.auction.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import tictoc.model.tictoc.TicTocStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@RedisHash("auction")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionRedis implements Serializable {
//    @Id
//    private Long id;
//    private Long auctioneerId;
//    private String title;
//    private String content;
//    private Integer startPrice;
//    private Integer currentPrice;
//    private Integer finalPrice;
//    private LocalDateTime sellStartTime;
//    private LocalDateTime sellEndTime;
//    private LocalDateTime auctionOpenTime;
//    private LocalDateTime auctionCloseTime;
//    private String progress;
//    private String type;
//    private TicTocStatus status;
//
//    public static AuctionRedis from(Auction auction) {
//        return AuctionRedis.builder()
//                .id(auction.getId())
//                .auctioneerId(auction.getAuctioneerId())
//                .finalPrice(auction.getFinalPrice())
//                .sellEndTime(auction.getSellEndTime())
//                .build();
//    }
}
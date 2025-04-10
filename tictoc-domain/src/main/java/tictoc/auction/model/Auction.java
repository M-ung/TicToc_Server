package tictoc.auction.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import tictoc.auction.dto.request.AuctionUseCaseReqDTO;
import tictoc.auction.exception.AuctionAlreadyStartedException;
import tictoc.auction.exception.AuctionNoAccessException;
import tictoc.bid.exception.AuctionAlreadyBidException;
import tictoc.auction.model.type.AuctionProgress;
import tictoc.auction.model.type.AuctionType;
import tictoc.bid.exception.BidException;
import tictoc.model.baseTime.BaseTimeEntity;
import tictoc.model.tictoc.TicTocStatus;
import java.time.LocalDateTime;
import static tictoc.auction.model.type.AuctionProgress.*;
import static tictoc.error.ErrorCode.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auction")
public class Auction extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long auctioneerId;
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private Integer startPrice;
    private Integer currentPrice;
    private Integer finalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sellStartTime; // 판매하고 싶은 시간(시작)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sellEndTime; // 판매하고 싶은 시간(종료)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime auctionOpenTime; // 경매 시작 시간
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime auctionCloseTime; // 경매 종료 시간
    @Enumerated(EnumType.STRING)
    private AuctionProgress progress;
    @Enumerated(EnumType.STRING)
    private AuctionType type;
    @Enumerated(EnumType.STRING)
    private TicTocStatus status;
    @Version
    private Long version;

    public static Auction of(final Long userId, AuctionUseCaseReqDTO.Register requestDTO) {
        return Auction.builder()
                .auctioneerId(userId)
                .title(requestDTO.title())
                .content(requestDTO.content())
                .startPrice(requestDTO.startPrice())
                .currentPrice(requestDTO.startPrice())
                .finalPrice(requestDTO.startPrice())
                .sellStartTime(requestDTO.sellStartTime())
                .sellEndTime(requestDTO.sellEndTime())
                .auctionOpenTime(LocalDateTime.now())
                .auctionCloseTime(requestDTO.auctionCloseTime())
                .progress(NOT_STARTED)
                .type(requestDTO.type())
                .status(TicTocStatus.ACTIVE)
                .build();
    }

    public void update(AuctionUseCaseReqDTO.Update requestDTO) {
        validateAuctionAlreadyStarted();
        this.title = requestDTO.title();
        this.content = requestDTO.content();
        this.startPrice = requestDTO.startPrice();
        this.currentPrice = requestDTO.startPrice();
        this.finalPrice = requestDTO.startPrice();
        this.sellStartTime = requestDTO.sellStartTime();
        this.sellEndTime = requestDTO.sellEndTime();
        this.auctionCloseTime = requestDTO.auctionCloseTime();
        this.type = requestDTO.type();
    }

    public void updateCurrentPrice(Integer price) {
        if(this.currentPrice < price) {
            this.currentPrice = price;
        } else {
            throw new BidException(BID_FAIL);
        }
    }

    public void deactivate(final Long userId) {
        validateAuctionAccess(userId);
        validateAuctionAlreadyStarted();
        this.status = TicTocStatus.DISACTIVE;
    }

    public void validateAuctionAccess(final Long userId) {
        if(!userId.equals(this.auctioneerId)) {
            throw new AuctionNoAccessException(AUCTION_NO_ACCESS);
        }
    }

    private void validateAuctionAlreadyStarted() {
        if(!this.getProgress().equals(NOT_STARTED)) {
            throw new AuctionAlreadyStartedException(AUCTION_ALREADY_STARTED);
        }
    }

    public void startBid(final Long userId) {
        this.validateBidAccess(userId);
        this.validateAuctionProgress();
        if (this.progress == NOT_STARTED) {
            this.progress = IN_PROGRESS;
        }
    }

    private void validateBidAccess(final Long userId) {
        if(userId.equals(this.auctioneerId)) {
            throw new AuctionNoAccessException(AUCTION_NO_ACCESS);
        }
    }

    private void validateAuctionProgress() {
        if (this.progress == BID || this.progress == NOT_BID) {
            throw new AuctionAlreadyBidException(AUCTION_ALREADY_FINISHED);
        }
    }

    public void bid() {
        this.finalPrice = this.currentPrice;
        this.progress = BID;
    }
    public void notBid() {
        this.currentPrice = 0;
        this.finalPrice = 0;
        this.progress = NOT_BID;
    }
}
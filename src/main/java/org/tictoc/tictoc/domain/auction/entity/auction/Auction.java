package org.tictoc.tictoc.domain.auction.entity.auction;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionNoAccessException;
import org.tictoc.tictoc.domain.auction.exception.bid.AuctionAlreadyBidException;
import org.tictoc.tictoc.domain.auction.exception.bid.BidNoAccessException;
import org.tictoc.tictoc.domain.auction.exception.bid.InvalidBidPriceException;
import org.tictoc.tictoc.global.common.entity.base.BaseTimeEntity;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionAlreadyStartedException;
import java.time.LocalDateTime;
import static org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress.*;
import static org.tictoc.tictoc.global.error.ErrorCode.*;
import static org.tictoc.tictoc.global.error.ErrorCode.AUCTION_ALREADY_STARTED;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private LocalDateTime sellStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sellEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime auctionOpenTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime auctionCloseTime;
    @Enumerated(EnumType.STRING)
    private AuctionProgress progress;
    @Enumerated(EnumType.STRING)
    private AuctionType type;
    @Enumerated(EnumType.STRING)
    private TicTocStatus status;
    @Version
    private Integer version;

    public static Auction of(final Long userId, AuctionRequestDTO.Register requestDTO) {
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

    public void update(AuctionRequestDTO.Update requestDTO) {
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

    public void deactivate(final Long userId) {
        validateAuctionAccess(userId);
        validateAuctionAlreadyStarted();
        this.status = TicTocStatus.DISACTIVE;
    }

    public void validateAuctionAccess(final Long userId) {
        if(!userId.equals(this.auctioneerId)) {
            throw new BidNoAccessException(BID_NO_ACCESS);
        }
    }

    private void validateAuctionAlreadyStarted() {
        if(!this.getProgress().equals(NOT_STARTED)) {
            throw new AuctionAlreadyStartedException(AUCTION_ALREADY_STARTED);
        }
    }

    public void startAuction(final Long userId) {
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
        if (this.progress == AuctionProgress.BID || this.progress == AuctionProgress.NOT_BID) {
            throw new AuctionAlreadyBidException(AUCTION_ALREADY_FINISHED);
        }
    }

    public void increaseBid(final Integer price) {
        validatePrice(price);
        this.currentPrice = price;
        this.finalPrice = price;
    }

    private void validatePrice(final Integer price) {
        if (this.currentPrice >= price) {
            throw new InvalidBidPriceException(INVALID_BID_PRICE);
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
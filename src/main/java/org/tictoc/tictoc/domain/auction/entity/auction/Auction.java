package org.tictoc.tictoc.domain.auction.entity.auction;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.tictoc.tictoc.domain.auction.dto.auction.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionNoAccessException;
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
        checkAuctionNotStarted();
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

    public void deactivate() {
        checkAuctionNotStarted();
        this.status = TicTocStatus.DISACTIVE;
    }

    public void increaseBid(Integer price) {
        this.currentPrice = price;
    }

    public void checkAuctionNotStarted() {
        if(!this.getProgress().equals(NOT_STARTED)) {
            throw new AuctionAlreadyStartedException(AUCTION_ALREADY_STARTED);
        }
    }

    public void close() {
        if (this.progress.equals(IN_PROGRESS)) {
            this.finalPrice = this.currentPrice;
            this.progress = BID;
        } else if (this.progress.equals(NOT_STARTED)) {
            this.currentPrice = 0;
            this.finalPrice = 0;
            this.progress = NOT_BID;
        } else {
            throw new AuctionNoAccessException(AUCTION_NO_ACCESS);
        }
    }
}
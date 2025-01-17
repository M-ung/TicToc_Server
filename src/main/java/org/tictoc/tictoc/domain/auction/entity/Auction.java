package org.tictoc.tictoc.domain.auction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.tictoc.tictoc.domain.auction.dto.request.AuctionRequestDTO;
import org.tictoc.tictoc.domain.auction.entity.location.Location;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionType;
import org.tictoc.tictoc.global.common.entity.BaseTimeEntity;
import org.tictoc.tictoc.global.common.entity.type.TicTocStatus;
import org.tictoc.tictoc.domain.auction.exception.AuctionAlreadyStartedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress.FINISHED;
import static org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress.NOT_PROGRESS;
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
    private LocalDateTime sellStartTime;
    private LocalDateTime sellEndTime;
    private LocalDateTime auctionOpenTime;
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
                .progress(NOT_PROGRESS)
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

    public void finished() {
        this.progress = FINISHED;
    }

    public void checkAuctionNotStarted() {
        if(!this.getProgress().equals(NOT_PROGRESS)) {
            throw new AuctionAlreadyStartedException(AUCTION_ALREADY_STARTED);
        }
    }
}
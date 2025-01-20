package org.tictoc.tictoc.infra.kafka.service.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tictoc.tictoc.domain.auction.entity.auction.Auction;
import org.tictoc.tictoc.domain.auction.entity.type.AuctionProgress;
import org.tictoc.tictoc.domain.auction.exception.auction.AuctionNotFoundException;
import org.tictoc.tictoc.domain.auction.repository.auction.AuctionRepository;
import org.tictoc.tictoc.global.common.entity.KafkaConstants;
import org.tictoc.tictoc.infra.kafka.dto.AuctionCloseMessageDTO;
import static org.tictoc.tictoc.global.error.ErrorCode.AUCTION_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AuctionCloseConsumer {
    private final AuctionRepository auctionRepository;

    @Transactional
    @KafkaListener(topics = KafkaConstants.AUCTION_CLOSE_TOPIC, groupId = KafkaConstants.AUCTION_GROUP_ID)
    public void handleAuctionClose(AuctionCloseMessageDTO message) throws InterruptedException {
        Thread.sleep(message.delayMillis());
        close(findAuctionById(message.auctionId()));
    }

    private void close(Auction auction) {
        if (auction.getProgress().equals(AuctionProgress.IN_PROGRESS)) {
            auction.bid();
        } else {
            auction.notBid();
        }
    }

    private Auction findAuctionById(final Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionNotFoundException(AUCTION_NOT_FOUND));
    }
}
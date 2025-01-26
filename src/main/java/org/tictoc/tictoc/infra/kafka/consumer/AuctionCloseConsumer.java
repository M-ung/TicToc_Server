package org.tictoc.tictoc.infra.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tictoc.tictoc.global.common.entity.KafkaConstants;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;
import org.tictoc.tictoc.infra.kafka.exception.KafkaAuctionCloseProcessingException;
import org.tictoc.tictoc.infra.kafka.service.AuctionCloseHandlerService;
import static org.tictoc.tictoc.global.error.ErrorCode.KAFKA_AUCTION_PROCESSING_ERROR;

@Component
@RequiredArgsConstructor
public class AuctionCloseConsumer {
    private final AuctionCloseHandlerService auctionCloseHandlerService;

    @KafkaListener(topics = KafkaConstants.AUCTION_CLOSE_TOPIC, groupId = KafkaConstants.AUCTION_CLOSE_GROUP_ID)
    public void handleAuctionClose(KafkaAuctionMessageDTO.auctionClose message) {
        try {
            auctionCloseHandlerService.process(message);
        } catch (Exception e) {
            throw new KafkaAuctionCloseProcessingException(KAFKA_AUCTION_PROCESSING_ERROR);
        }
    }
}
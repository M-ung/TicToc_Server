package org.tictoc.tictoc.infra.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.tictoc.tictoc.global.common.entity.constants.KafkaConstants;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;
import org.tictoc.tictoc.infra.kafka.consumer.service.AuctionCloseHandlerService;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuctionCloseConsumer {
    private final AuctionCloseHandlerService auctionCloseHandlerService;
    private final TaskScheduler auctionCloseScheduler;

    @KafkaListener(topics = KafkaConstants.AUCTION_CLOSE_TOPIC, groupId = KafkaConstants.AUCTION_CLOSE_GROUP_ID)
    public void handleAuctionClose(KafkaAuctionMessageDTO.AuctionClose message) {
        auctionCloseScheduler.schedule(() -> auctionCloseHandlerService.process(message), new Date(System.currentTimeMillis() + message.delayMillis()));
    }
}
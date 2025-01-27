package org.tictoc.tictoc.infra.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.tictoc.tictoc.global.common.entity.constants.KafkaConstants;
import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;

@Component
@RequiredArgsConstructor
public class AuctionCloseProducer {
    private final KafkaTemplate<Object, KafkaAuctionMessageDTO.AuctionClose> kafkaTemplate;

    public void send(KafkaAuctionMessageDTO.AuctionClose messageDTO) {
        kafkaTemplate.send(KafkaConstants.AUCTION_CLOSE_TOPIC, messageDTO);
    }
}
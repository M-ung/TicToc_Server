package org.tictoc.tictoc.infra.kafka.consumer.service;

import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;

public interface AuctionCloseHandlerService {
    void process(KafkaAuctionMessageDTO.AuctionClose message);
}

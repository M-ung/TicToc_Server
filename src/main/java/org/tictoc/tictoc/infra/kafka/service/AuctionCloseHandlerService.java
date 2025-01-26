package org.tictoc.tictoc.infra.kafka.service;

import org.tictoc.tictoc.infra.kafka.dto.KafkaAuctionMessageDTO;

public interface AuctionCloseHandlerService {
    void process(KafkaAuctionMessageDTO.auctionClose message);
}

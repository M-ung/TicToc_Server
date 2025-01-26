package org.tictoc.tictoc.infra.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.tictoc.tictoc.global.common.entity.KafkaConstants;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic auctionCloseTopic() {
        return TopicBuilder.name(KafkaConstants.AUCTION_CLOSE_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
package org.tictoc.tictoc.infra.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic auctionCloseTopic() {
        return TopicBuilder.name("auction-close-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
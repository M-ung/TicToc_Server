package tictoc.conifg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import tictoc.auction.event.AuctionCloseListener;

@Configuration
public class RedisMessageConfig {
    private static final String AUCTION_CLOSE_CHANNEL = "auction:close";

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListenerAdapter, new ChannelTopic(AUCTION_CLOSE_CHANNEL));
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(AuctionCloseListener auctionCloseListener) {
        return new MessageListenerAdapter(auctionCloseListener, "handleAuctionCloseEvent");
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic(AUCTION_CLOSE_CHANNEL);
    }
}
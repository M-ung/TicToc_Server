package tictoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import tictoc.auction.event.CloseAuctionEventListener;
import tictoc.constants.RedisConstants;

@Configuration
public class RedisMessageConfig {
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory, CloseAuctionEventListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listener, new PatternTopic(RedisConstants.REDIS_KEY_EVENT_EXPIRED));
        return container;
    }

    @Bean
    public Boolean enableKeyspaceNotifications(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.execute((RedisCallback<Object>) connection -> {
            connection.setConfig(RedisConstants.REDIS_KEY_SPACE_EVENT_PARAM, RedisConstants.REDIS_KEY_SPACE_EVENT_VALUE);
            return null;
        });
        return true;
    }
}
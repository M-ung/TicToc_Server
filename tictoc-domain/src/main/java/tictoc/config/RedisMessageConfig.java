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
    /**
     * Redis의 Key Expire 이벤트를 수신할 RedisMessageListenerContainer 빈 등록
     * - 특정 키가 TTL 만료되어 삭제되었을 때, 리스너가 이벤트를 수신
     * - 리스너: CloseAuctionEventListener
     * - 토픽: "__keyevent@*__:expired"
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory cf, CloseAuctionEventListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(cf);
        container.addMessageListener(listener, new PatternTopic(RedisConstants.REDIS_KEY_EVENT_EXPIRED)); // REDIS_KEY_EVENT_EXPIRED 토픽 감지
        return container;
    }

    /**
     * Redis 서버의 Keyspace Notifications 설정을 활성화하는 빈
     * - notify-keyspace-events 설정 값을 "Ex"로 지정
     *   "E": 이벤트 알림 활성화
     *   "x": 키 만료 이벤트(expired) 포함
     */
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
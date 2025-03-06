package tictoc.kafka.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import tictoc.kafka.exception.KafkaSettingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static tictoc.error.ErrorCode.KAFKA_SETTING_ERROR;

@Configuration
public class KafkaAdminConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public ApplicationRunner configureTopicRetention(KafkaAdmin kafkaAdmin) {
        return args -> {
            try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
                ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, "user-login-history-topic");
                Map<ConfigResource, Config> updateConfigs = Map.of(
                        resource, new Config(Collections.singletonList(
                                new ConfigEntry("retention.ms", "60000"))
                        )
                );
                adminClient.alterConfigs(updateConfigs).all().get();
            } catch (Exception e) {
                throw new KafkaSettingException(KAFKA_SETTING_ERROR);
            }
        };
    }
}
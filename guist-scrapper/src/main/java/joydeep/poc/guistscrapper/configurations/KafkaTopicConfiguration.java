package joydeep.poc.guistscrapper.configurations;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

    private static final String DEPT_TOPIC="dept-topic";
    private static final String FAC_TOPIC="fact-topic";
    @Value(value = "${kafka.address}")
    private String kafkaAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(DEPT_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(FAC_TOPIC, 1, (short) 1);
    }

}


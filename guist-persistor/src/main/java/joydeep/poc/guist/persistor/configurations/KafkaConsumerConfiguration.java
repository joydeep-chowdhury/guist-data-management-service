package joydeep.poc.guist.persistor.configurations;

import joydeep.poc.guist.persistor.domains.Department;
import joydeep.poc.guist.persistor.domains.Faculty;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    private static final String GROUPID="guist-group";
    @Value(value = "${kafka.address}")
    private String kafkaAddress;

    @Bean
    public ConsumerFactory<String, Department> departmentConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUPID);
        JsonDeserializer<Department> deserializer = new JsonDeserializer<>(Department.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConsumerFactory<String, Faculty> facultyConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUPID);
        JsonDeserializer<Faculty> deserializer = new JsonDeserializer<>(Faculty.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Department> departmentKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Department> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(departmentConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Faculty> facultyKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Faculty> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(facultyConsumerFactory());
        return factory;
    }

}

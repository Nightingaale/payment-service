package org.nightingaale.paymentservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.nightingaale.paymentservice.dto.PaymentTransactionDto;
import org.nightingaale.paymentservice.dto.RefundTransactionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, PaymentTransactionDto> paymentTransactionProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseConfig());
    }

    @Bean
    public KafkaTemplate<String, PaymentTransactionDto> paymentTransactionKafkaTemplate() {
        return new KafkaTemplate<>(paymentTransactionProducerFactory());
    }

    @Bean
    public ProducerFactory<String, RefundTransactionDto> refundTransactionProducerFactory() {
        return new DefaultKafkaProducerFactory<>(baseConfig());
    }

    @Bean
    public KafkaTemplate<String, RefundTransactionDto> refundTransactionKafkaTemplate() {
        return new KafkaTemplate<>(refundTransactionProducerFactory());
    }
}
package org.nightingaale.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name("payment-transaction-create")
                .build();
    }

    @Bean
    public NewTopic newTopic2() {
        return TopicBuilder.name("refund-transaction-create")
                .build();
    }

}

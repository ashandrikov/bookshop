package com.shandrikov.bookshop.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty("${kafka.enable}")
public class KafkaConfiguration {
    @Value("${main.topic}")
    private String mainTopic;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(mainTopic)
                .partitions(3)
                .build();
    }
}

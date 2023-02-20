package com.shandrikov.bookshop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Value("${main.topic}")
    private String mainTopic;

//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name(mainTopic)
//                .partitions(3)
//                .build();
//    }
}

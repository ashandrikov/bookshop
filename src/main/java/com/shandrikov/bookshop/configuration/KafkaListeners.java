package com.shandrikov.bookshop.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty("${kafka.enable}")
public class KafkaListeners {
    @KafkaListener(
            topics = "${main.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    void listener(String data){
        log.info("Data received: " + data);
    }
}

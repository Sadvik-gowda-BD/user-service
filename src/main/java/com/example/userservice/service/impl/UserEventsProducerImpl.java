package com.example.userservice.service.impl;

import com.example.userservice.service.UserEventsProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserEventsProducerImpl implements UserEventsProducer {

    @Value("${user.event.topic.name}")
    String userEventTopic;

    final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserEventsProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishUserEvents(String key, String message) {
        log.info("Publishing user-event: {}", message);
        kafkaTemplate.send(userEventTopic, key, message);
        log.info("Successfully sent msg to kafka topic");
    }
}

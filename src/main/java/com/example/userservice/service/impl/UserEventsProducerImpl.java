package com.example.userservice.service.impl;

import com.example.userservice.dto.EventDto;
import com.example.userservice.service.AuthenticationService;
import com.example.userservice.service.UserEventsProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEventsProducerImpl implements UserEventsProducer {

    @Value("${user.event.topic.name}")
    String userEventTopic;

    final KafkaTemplate<String, EventDto> kafkaTemplate;
    final AuthenticationService authenticationService;

    @Override
    public void publishUserEvents(String key, EventDto value) {
        log.info("Publishing user-event: [KEY]:{} [VALUE]:{}", key, value);
        kafkaTemplate.send(userEventTopic, key, value);
        log.info("Successfully sent msg to kafka topic");
    }
}

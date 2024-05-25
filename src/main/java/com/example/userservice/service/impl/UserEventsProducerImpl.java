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
    public void publishUserEvents(String key, String message) {
        log.info("Publishing user-event: [KEY]:{} [VALUE]:{}", key, message);
        //TODO: remove dummy event
        EventDto d = EventDto.builder()
                .accessedBy("101")
                .accessedFor("201")
                .message(message)
                .build();
        kafkaTemplate.send(userEventTopic, key, d);
        log.info("Successfully sent msg to kafka topic");
    }
}

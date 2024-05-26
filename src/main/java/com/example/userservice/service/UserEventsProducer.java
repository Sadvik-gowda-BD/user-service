package com.example.userservice.service;

import com.example.userservice.dto.EventDto;

public interface UserEventsProducer {

    void publishUserEvents(String key, EventDto event);
}

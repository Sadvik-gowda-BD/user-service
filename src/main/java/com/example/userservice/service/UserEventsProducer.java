package com.example.userservice.service;

public interface UserEventsProducer {

    void publishUserEvents(String key, String message);
}

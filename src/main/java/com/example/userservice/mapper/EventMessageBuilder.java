package com.example.userservice.mapper;

public class EventMessageBuilder {

    public static String buildEventMessage(String message, String accessedBy, String accessFor) {
        return new StringBuilder("action: [").append(message).append("]. accessedBy: [").append(accessedBy)
                .append("]. accessedFor: [").append(accessFor).append("]").toString();
    }
}

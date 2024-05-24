package com.example.userservice.utils;

import java.time.LocalDateTime;

public class UserEventBuilder {

    public static String buildUserEventMessage(String accessedBy, String accessedFor, String accessType) {
        StringBuilder sb = new StringBuilder("User ");
        return sb.append(accessedBy).append(" ")
                .append(accessType).append(" ")
                .append(accessedFor).append(" data.")
                .append("Access Time:").append(LocalDateTime.now()).toString();
    }
}

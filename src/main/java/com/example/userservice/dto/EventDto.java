package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventDto {
    String message;
    String accessedBy;
    String accessedFor;
}

package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponseDto{

    private long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailId;
    private String role;
}

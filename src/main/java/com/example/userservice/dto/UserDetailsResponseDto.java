package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponseDto extends GenericResponseDto{

    private long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailId;
}

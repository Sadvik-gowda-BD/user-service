package com.example.userservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
    private long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailId;
}

package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    @NotEmpty(message = "First name should not be empty")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "Last name should not be empty")
    private String lastName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid email address")
    private String emailId;
    @NotEmpty(message = "Date of birth should not be empty")
    private String dateOfBirth;
}

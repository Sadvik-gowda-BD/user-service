package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import static com.example.userservice.utils.Constant.*;

@Builder
@Data
public class UserRegisterDto {

    @NotBlank(message = INVALID_FIRST_NAME)
    private String firstName;
    private String middleName;
    @NotBlank(message = INVALID_LAST_NAME)
    private String lastName;
    @Email(regexp = EMAIL_REGEX, flags = Pattern.Flag.CASE_INSENSITIVE,
            message = INVALID_EMAIL_ADDRESS)
    @NotBlank(message = INVALID_EMAIL_ADDRESS)
    private String emailId;
    @NotBlank(message = INVALID_PASSWORD_NAME)
    private String password;
    @NotBlank(message = INVALID_ROLE)
    private String role;
}

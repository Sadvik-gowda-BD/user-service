package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterDto {

    @NotBlank(message = "First name should not be empty.")
    private String firstName;
    private String middleName;
    @NotBlank(message = "Last name should not be empty.")
    private String lastName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid email address.")
    private String emailId;
    @NotBlank(message = "Password should not be empty.")
    private String password;
    @NotBlank(message = "Roles should not be blank, it should be either USER or ADMIN.")
    private String role;
}

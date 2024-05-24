package com.example.userservice.controller;

import com.example.userservice.dto.GenericResponseDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static com.example.userservice.constant.TestConstant.EMAIL_ID;
import static com.example.userservice.constant.TestConstant.FIRST_NAME;
import static com.example.userservice.constant.TestConstant.LAST_NAME;
import static com.example.userservice.constant.TestConstant.MIDDLE_NAME;
import static com.example.userservice.constant.TestConstant.ROLE_USER;
import static com.example.userservice.constant.TestConstant.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;
    @Mock
    UserService userService;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testRegister() {
        String responseMessage = String.format("Registered successfully. User id is %s", USER_ID);
        UserRegisterDto registerRequestDto = UserRegisterDto.builder()
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .lastName(LAST_NAME)
                .role(ROLE_USER)
                .build();

        when(userService.registerUser(registerRequestDto)).thenReturn(USER_ID);
        ResponseEntity<GenericResponseDto> response = userController.register(registerRequestDto);
        GenericResponseDto responseDto = response.getBody();

        assertNotNull(response);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getMessage());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertTrue(responseDto.isResult());
        assertEquals(responseMessage, responseDto.getMessage());
        Optional<ConstraintViolation<UserRegisterDto>> violation = validator.validate(registerRequestDto).stream().findFirst();
        assertFalse(violation.isPresent());
    }

    @Test
    void testRegisterFirstNameValidation() {
        String firstNameErrorMessage = "First name should not be empty";
        UserRegisterDto registerRequestDto = UserRegisterDto.builder()
                .firstName(null)
                .middleName(MIDDLE_NAME)
                .lastName(LAST_NAME)
                .role(ROLE_USER)
                .build();

        Optional<ConstraintViolation<UserRegisterDto>> violation = validator.validate(registerRequestDto).stream().findFirst();
        assertTrue(violation.isPresent());
        ConstraintViolation<UserRegisterDto> result = violation.get();
        assertEquals(firstNameErrorMessage, result.getMessage());
    }

    @Test
    void testRegisterLastNameValidation() {
        String firstNameErrorMessage = "Last name should not be empty";
        UserRegisterDto registerRequestDto = UserRegisterDto.builder()
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .lastName(null)
                .role(ROLE_USER)
                .build();

        Optional<ConstraintViolation<UserRegisterDto>> violation = validator.validate(registerRequestDto).stream().findFirst();
        assertTrue(violation.isPresent());
        ConstraintViolation<UserRegisterDto> result = violation.get();
        assertEquals(firstNameErrorMessage, result.getMessage());
    }

    @Test
    void testGetUserDetailsById() {
        UserDetailsResponseDto userDetailsResponseDto = UserDetailsResponseDto.builder()
                .userId(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .emailId(EMAIL_ID)
                .build();
        when(userService.getUserById(USER_ID)).thenReturn(userDetailsResponseDto);
        ResponseEntity<UserDetailsResponseDto> response = userController.getUserDetailsById(USER_ID);
        UserDetailsResponseDto responseDto = response.getBody();

        assertNotNull(response);
        assertNotNull(responseDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(responseDto.isResult());
        assertEquals(userDetailsResponseDto.getFirstName(), responseDto.getFirstName());
        assertEquals(userDetailsResponseDto.getLastName(), responseDto.getLastName());
        assertEquals(userDetailsResponseDto.getEmailId(), responseDto.getEmailId());

    }

    //TODO: Write junit test cases for other methods


}

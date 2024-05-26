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

import static com.example.userservice.constant.TestConstant.*;
import static com.example.userservice.utils.Constant.*;
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
                .role(TEST_USER_ROLE)
                .password(PASSWORD)
                .emailId(EMAIL_ID)
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
        UserRegisterDto registerRequestDto = UserRegisterDto.builder()
                .firstName(null)
                .middleName(MIDDLE_NAME)
                .lastName(LAST_NAME)
                .role(TEST_USER_ROLE)
                .password(PASSWORD)
                .emailId(EMAIL_ID)
                .build();

        Optional<ConstraintViolation<UserRegisterDto>> violation = validator.validate(registerRequestDto).stream().findFirst();
        assertTrue(violation.isPresent());
        ConstraintViolation<UserRegisterDto> result = violation.get();
        assertEquals(INVALID_FIRST_NAME, result.getMessage());
    }

    @Test
    void testRegisterLastNameValidation() {
        UserRegisterDto registerRequestDto = UserRegisterDto.builder()
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .lastName(null)
                .role(TEST_USER_ROLE)
                .password(PASSWORD)
                .emailId(EMAIL_ID)
                .build();

        Optional<ConstraintViolation<UserRegisterDto>> violation = validator.validate(registerRequestDto).stream().findFirst();
        assertTrue(violation.isPresent());
        ConstraintViolation<UserRegisterDto> result = violation.get();
        assertEquals(INVALID_LAST_NAME, result.getMessage());
    }


    @Test
    void testRegisterEmailIdValidation() {
        UserRegisterDto registerRequestDto = UserRegisterDto.builder()
                .firstName(FIRST_NAME)
                .middleName(MIDDLE_NAME)
                .lastName(LAST_NAME)
                .role(TEST_USER_ROLE)
                .emailId("abc")
                .password(PASSWORD)
                .build();

        Optional<ConstraintViolation<UserRegisterDto>> violation = validator.validate(registerRequestDto).stream().findFirst();
        assertTrue(violation.isPresent());
        ConstraintViolation<UserRegisterDto> result = violation.get();
        assertEquals(INVALID_EMAIL_ADDRESS, result.getMessage());
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
        assertEquals(userDetailsResponseDto.getFirstName(), responseDto.getFirstName());
        assertEquals(userDetailsResponseDto.getLastName(), responseDto.getLastName());
        assertEquals(userDetailsResponseDto.getEmailId(), responseDto.getEmailId());

    }

    //TODO: Write junit all positive and negative test cases for other methods

}

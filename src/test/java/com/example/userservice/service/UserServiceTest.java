package com.example.userservice.service;

import com.example.userservice.dto.UserDetailsDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.UserCredentialEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.UserNotFound;
import com.example.userservice.mapper.UserCredentialMapper;
import com.example.userservice.repository.UserCredentialRepo;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.userservice.constant.TestConstant.EMAIL_ID;
import static com.example.userservice.constant.TestConstant.FIRST_NAME;
import static com.example.userservice.constant.TestConstant.LAST_NAME;
import static com.example.userservice.constant.TestConstant.PASSWORD;
import static com.example.userservice.constant.TestConstant.ROLE_USER;
import static com.example.userservice.constant.TestConstant.USER_ID;
import static com.example.userservice.constant.TestConstant.USER_NOT_FOUND_EX_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    static UserEntity userEntity;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserCredentialRepo userCredentialRepo;
    @Mock
    UserCredentialMapper userCredentialMapper;
    @Mock
    AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        userEntity = UserEntity.builder()
                .userId(USER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .emailId(EMAIL_ID)
                .build();
    }

    @Test
    void testRegisterUser() {

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .password(PASSWORD)
                .role(ROLE_USER)
                .build();

        UserCredentialEntity userCredential = UserCredentialEntity.builder()
                .userId(USER_ID)
                .password(PASSWORD)
                .role(ROLE_USER)
                .build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userCredentialMapper.map(USER_ID, userRegisterDto)).thenReturn(userCredential);

        long responseUserId = userService.registerUser(userRegisterDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userCredentialRepo, times(1)).save(any(UserCredentialEntity.class));

        assertEquals(USER_ID, responseUserId);
    }

    @Test
    void testGetUserById() {

        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(userEntity));

        UserDetailsResponseDto responseDto = userService.getUserById(USER_ID);
        assertNotNull(responseDto);
        assertEquals(userEntity.getUserId(), responseDto.getUserId());
        assertEquals(userEntity.getFirstName(), responseDto.getFirstName());
        assertEquals(userEntity.getLastName(), responseDto.getLastName());
        assertEquals(userEntity.getEmailId(), responseDto.getEmailId());
    }

    @Test
    void testGetUserByIdWhenUserNotFound() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        UserNotFound exception = assertThrows(UserNotFound.class, () -> userService.getUserById(USER_ID));
        assertEquals(USER_NOT_FOUND_EX_MESSAGE, exception.getMessage());
    }

    @Test
    void deleteUserDetailsTest() {
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(userEntity));

        userService.deleteUserDetails(USER_ID);
        verify(userRepository).delete(userEntityCaptor.capture());
        verify(userCredentialRepo).deleteById(USER_ID);

        UserEntity deletedUserEntity = userEntityCaptor.getValue();
        assertEquals(USER_ID, deletedUserEntity.getUserId());
    }

    @Test
    void testGetAllUsers(){
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userEntity));
        List<UserDetailsDto> userDtos = userService.getAllUsers();

        UserDetailsDto userDto = userDtos.get(0);
        assertNotNull(userDto);
        assertEquals(userEntity.getUserId(), userDto.getUserId());
        assertEquals(userEntity.getFirstName(), userDto.getFirstName());
        assertEquals(userEntity.getLastName(), userDto.getLastName());
        assertEquals(userEntity.getEmailId(), userDto.getEmailId());
    }

    @Test
    void testGetCurrentUserDetails(){
        when(authenticationService.getCurrentUser()).thenReturn(String.valueOf(USER_ID));
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(userEntity));
        UserDetailsResponseDto userDto = userService.getCurrentUserDetails();
        assertNotNull(userDto);
        assertEquals(userEntity.getUserId(), userDto.getUserId());
        assertEquals(userEntity.getFirstName(), userDto.getFirstName());
        assertEquals(userEntity.getLastName(), userDto.getLastName());
        assertEquals(userEntity.getEmailId(), userDto.getEmailId());
    }

    void testUpdateUserDetails(){
        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(userEntity));


    }
}

package com.example.userservice.service;

import com.example.userservice.dto.UserDetailsDto;
import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;

import java.util.List;

public interface UserService {

    long registerUser(UserRegisterDto userRegisterDto);

    UserDetailsResponseDto getUserById(long userId);
    UserDetailsResponseDto getCurrentUserDetails();

    UserDetailsResponseDto updateUserDetails(UserDetailsRequestDto userDetailsDto);

    void deleteUserDetails(long userId);

    List<UserDetailsDto> getAllUsers();
}

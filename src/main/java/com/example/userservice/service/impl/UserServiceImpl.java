package com.example.userservice.service.impl;

import com.example.userservice.dto.UserDetailsDto;
import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.UserNotFound;
import com.example.userservice.mapper.UserDetailsMapper;
import com.example.userservice.repository.UserCredentialRepo;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.AuthenticationService;
import com.example.userservice.service.UserService;
import com.example.userservice.mapper.UserCredentialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCredentialRepo userCredentialRepo;
    private final UserCredentialMapper userCredentialMapper;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public long registerUser(UserRegisterDto userRegisterDto) {
        UserEntity userEntityRequest = UserDetailsMapper.map(userRegisterDto);
        UserEntity responseEntity = userRepository.save(userEntityRequest);
        userCredentialRepo.save(userCredentialMapper.
                map(responseEntity.getUserId(), userRegisterDto));
        return responseEntity.getUserId();
    }

    @Override
    public UserDetailsResponseDto getUserById(long userId) {
        UserEntity userEntity = getUserEntity(userId);
        UserDetailsResponseDto userDetailsDto = new UserDetailsResponseDto();
        BeanUtils.copyProperties(userEntity, userDetailsDto);
        return userDetailsDto;
    }

    @Override
    public UserDetailsResponseDto getCurrentUserDetails() {
        String currentUserId = authenticationService.getCurrentUser();
        return getUserById(Long.parseLong(currentUserId));
    }

    @Override
    public UserDetailsResponseDto updateUserDetails(UserDetailsRequestDto userDetailsDto) {
        UserEntity requestEntity = getUserEntity(userDetailsDto.getUserId());
        BeanUtils.copyProperties(userDetailsDto, requestEntity);
        UserEntity responseEntity = userRepository.save(requestEntity);
        UserDetailsResponseDto responseDto = new UserDetailsResponseDto();
        BeanUtils.copyProperties(responseEntity, responseDto);
        return responseDto;
    }

    @Override
    @Transactional
    public void deleteUserDetails(long userId) {
        userRepository.delete(getUserEntity(userId));
        userCredentialRepo.deleteById(userId);
    }

    @Override
    public List<UserDetailsDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> {
                    UserDetailsDto responseDto = new UserDetailsDto();
                    BeanUtils.copyProperties(userEntity, responseDto);
                    return responseDto;
                }).toList();
    }

    private UserEntity getUserEntity(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found"));
    }
}

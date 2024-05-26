package com.example.userservice.service.impl;

import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.RoleEntity;
import com.example.userservice.entity.UserCredentialEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.InvaildRoleException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserDetailsMapper;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserCredentialRepo;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.AuthenticationService;
import com.example.userservice.service.UserService;
import com.example.userservice.mapper.UserCredentialMapper;
import com.example.userservice.validator.RolesValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.userservice.utils.Constant.INVALID_ROLE_EX_MESSAGE;
import static com.example.userservice.utils.Constant.USER_NOT_FOUND_EX_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCredentialRepo userCredentialRepo;
    private final UserCredentialMapper userCredentialMapper;
    private final AuthenticationService authenticationService;
    private final UserDetailsMapper userDetailsMapper;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public long registerUser(UserRegisterDto userRegisterDto) {
        UserEntity userEntityRequest = UserDetailsMapper.mapToEntity(userRegisterDto);
        UserEntity responseEntity = userRepository.save(userEntityRequest);
        userCredentialRepo.save(userCredentialMapper.
                map(responseEntity.getUserId(), userRegisterDto));
        return responseEntity.getUserId();
    }

    @Override
    public UserDetailsResponseDto getUserById(long userId) {
        UserEntity userEntity = getUserEntity(userId);
        return userDetailsMapper.mapToUserDetailResponse(userEntity);
    }

    @Override
    public UserDetailsResponseDto getCurrentUserDetails() {
        String currentUserId = authenticationService.getCurrentUser();
        return getUserById(Long.parseLong(currentUserId));
    }

    @Override
    @Transactional
    public UserDetailsResponseDto updateUserDetails(UserDetailsRequestDto userDetailsDto) {
        UserEntity requestEntity = getUserEntity(userDetailsDto.getUserId());
        BeanUtils.copyProperties(userDetailsDto, requestEntity);
        this.updateUserRole(userDetailsDto.getUserId(), userDetailsDto.getRole());
        UserEntity responseEntity = userRepository.save(requestEntity);
        return userDetailsMapper.mapToUserDetailResponse(responseEntity);
    }

    @Override
    @Transactional
    public void deleteUserDetails(long userId) {
        userRepository.delete(getUserEntity(userId));
        userCredentialRepo.deleteById(userId);
    }

    @Override
    public List<UserDetailsResponseDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userDetailsMapper::mapToUserDetailResponse).toList();
    }

    private UserEntity getUserEntity(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EX_MESSAGE));
    }

    public void updateUserRole(long userId, String role) {
        RolesValidator.validateRole(role);
        UserCredentialEntity userCredential = userCredentialRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EX_MESSAGE));
        if (!role.equals(userCredential.getRole())) {
            RoleEntity roleEntity = roleRepository.findByRole(role)
                    .orElseThrow(() -> new InvaildRoleException(INVALID_ROLE_EX_MESSAGE));
            userCredential.setRole(roleEntity);
            userCredentialRepo.save(userCredential);
        }
    }
}

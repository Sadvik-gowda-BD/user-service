package com.example.userservice.mapper;

import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.UserCredentialEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.repository.UserCredentialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsMapper {

    @Autowired
    final UserCredentialRepo userCredentialRepo;

    public static UserEntity mapToEntity(UserRegisterDto userRegisterDto) {
        return UserEntity.builder()
                .firstName(userRegisterDto.getFirstName())
                .middleName(userRegisterDto.getMiddleName())
                .lastName(userRegisterDto.getLastName())
                .emailId(userRegisterDto.getEmailId())
                .build();
    }

    public UserDetailsResponseDto mapToUserDetailResponse(UserEntity userEntity) {
        Optional<UserCredentialEntity> userCredential = userCredentialRepo.findById(userEntity.getUserId());
        return UserDetailsResponseDto.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .middleName(userEntity.getMiddleName())
                .lastName(userEntity.getLastName())
                .emailId(userEntity.getEmailId())
                .role(userCredential.get().getRole().getRole())
                .build();
    }

}

package com.example.userservice.mapper;

import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.UserCredentialEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserCredentialMapper {

    private final PasswordEncoder passwordEncoder;

    public UserCredentialEntity map(long userId, UserRegisterDto registerDto) {
        return new UserCredentialEntity(userId, getEncryptedPassword(registerDto.getPassword()), registerDto.getRole());
    }

    private String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

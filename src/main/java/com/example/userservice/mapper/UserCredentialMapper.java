package com.example.userservice.mapper;

import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.RoleEntity;
import com.example.userservice.entity.UserCredentialEntity;
import com.example.userservice.exception.InvalidRoleException;
import com.example.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.example.userservice.utils.Constant.INVALID_ROLE_EX_MESSAGE;

@RequiredArgsConstructor
@Component
public class UserCredentialMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserCredentialEntity map(long userId, UserRegisterDto registerDto) {
        RoleEntity roleEntity = roleRepository.findByRole(registerDto.getRole())
                .orElseThrow(() -> new InvalidRoleException(INVALID_ROLE_EX_MESSAGE));
        return new UserCredentialEntity(userId, getEncryptedPassword(registerDto.getPassword()), roleEntity);
    }

    private String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

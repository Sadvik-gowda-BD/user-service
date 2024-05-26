package com.example.userservice.service.impl;

import com.example.userservice.dto.LoginUserDetails;
import com.example.userservice.entity.UserCredentialEntity;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserCredentialRepo;
import com.example.userservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.userservice.utils.Constant.USER_NOT_FOUND_EX_MESSAGE;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserCredentialRepo userCredentialRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredentialEntity credentials = userCredentialRepo.findById(Long.valueOf(username))
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EX_MESSAGE));
        return new LoginUserDetails(credentials);
    }

    @Override
    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

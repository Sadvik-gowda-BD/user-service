package com.example.userservice.controller;

import com.example.userservice.dto.GenericResponseDto;
import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private  UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        long userId = userService.registerUser(userRegisterDto);
        GenericResponseDto responseDto = GenericResponseDto.builder()
                .result(true)
                .message(String.format("Registered successfully. User id is %s", userId))
                .build();
        return ResponseEntity.accepted().body(responseDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/details/{userId}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetailsById(@PathVariable("userId") long userId) {
        UserDetailsResponseDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/details")
    public ResponseEntity<UserDetailsResponseDto> getCurrentUserDetails() {
        UserDetailsResponseDto responseDto = userService.getCurrentUserDetails();
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/details/all")
    public ResponseEntity<List<UserDetailsResponseDto>> getAllUserDetails() {
        List<UserDetailsResponseDto> responseDto = userService.getAllUsers();
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<UserDetailsResponseDto> updateUserDetails(@RequestBody UserDetailsRequestDto userDetailsDto) {
        UserDetailsResponseDto responseDto = userService.updateUserDetails(userDetailsDto);
        return ResponseEntity.ok(responseDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<GenericResponseDto> deleteUserDetails(@PathVariable("userId") long userId) {
        userService.deleteUserDetails(userId);
        GenericResponseDto responseDto = GenericResponseDto.builder()
                .result(true)
                .message(String.format("Deleted user %s successfully", userId))
                .build();
        return ResponseEntity.ok(responseDto);
    }

}

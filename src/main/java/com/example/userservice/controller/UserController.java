package com.example.userservice.controller;

import com.example.userservice.dto.GenericResponseDto;
import com.example.userservice.dto.UserDetailsDto;
import com.example.userservice.dto.UserDetailsRequestDto;
import com.example.userservice.dto.UserDetailsResponseDto;
import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.service.UserEventsProducer;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserEventsProducer userEventsProducer;

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        long userId = userService.registerUser(userRegisterDto);
        GenericResponseDto responseDto = GenericResponseDto.builder()
                .result(true)
                .message(String.format("Registered successfully. User id is %s", userId))
                .build();
       // userEventsProducer.publishUserEvents("1","message");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@PathVariable("userId") long userId) {
        UserDetailsResponseDto responseDto = userService.getUserByid(userId);
        responseDto.setResult(true);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDetailsDto>> getAllUserDetails() {
        List<UserDetailsDto> responseDto = userService.getAllUsers();
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDetailsResponseDto> updateUserDetails(@RequestBody UserDetailsRequestDto userDetailsDto) {
        UserDetailsResponseDto responseDto = userService.updateUserDetails(userDetailsDto);
        responseDto.setResult(true);
        responseDto.setMessage("User details updated successfully");
        return ResponseEntity.ok(responseDto);
    }

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

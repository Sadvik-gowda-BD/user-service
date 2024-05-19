package com.example.userservice.mapper;

import com.example.userservice.dto.UserRegisterDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.utils.DateTimeConverter;

public class UserDetailsMapper {

    public static UserEntity map(UserRegisterDto userRegisterDto) {
        return UserEntity.builder()
                .firstName(userRegisterDto.getFirstName())
                .middleName(userRegisterDto.getMiddleName())
                .lastName(userRegisterDto.getLastName())
                .emailId(userRegisterDto.getEmailId())
                .dateOfBirth(DateTimeConverter.convertToDate(userRegisterDto.getDateOfBirth()))
                .build();
    }

}

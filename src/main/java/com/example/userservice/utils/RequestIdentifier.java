package com.example.userservice.utils;

import com.example.userservice.enums.ApiName;

public class RequestIdentifier {

    public static ApiName find(String signature) {
        if (signature.contains("com.example.userservice.controller.UserController.register(UserRegisterDto)")) {
            return ApiName.REGISTER;
        } else if (signature.contains("com.example.userservice.controller.UserController.getCurrentUserDetails()")) {
            return ApiName.GET_CURRENT_USER_DETAILS;
        } else if (signature.contains("com.example.userservice.controller.UserController.getUserDetailsById(long)")) {
            return ApiName.GET_DETAILS_BY_ID;
        } else if (signature.contains("com.example.userservice.controller.UserController.getAllUserDetails()")) {
            return ApiName.GET_ALL_DETAILS;
        } else if (signature.contains("com.example.userservice.controller.UserController.updateUserDetails(UserDetailsRequestDto)")) {
            return ApiName.UPDATE_USER_DETAILS;
        } else if (signature.contains("com.example.userservice.controller.UserController.deleteUserDetails(long)")) {
            return ApiName.DELETE_BY_ID;
        }
        return ApiName.UNKNOWN;
    }
}

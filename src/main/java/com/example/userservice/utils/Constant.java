package com.example.userservice.utils;

import java.util.Set;

public final class Constant {

    public static final String EMAIL_REGEX = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

    //Registration error messages
    public static final String INVALID_FIRST_NAME = "First name should not be empty.";
    public static final String INVALID_LAST_NAME = "Last name should not be empty.";
    public static final String INVALID_EMAIL_ADDRESS = "Invalid email address.";
    public static final String INVALID_PASSWORD_NAME = "Password should not be empty.";
    public static final String INVALID_ROLE = "Roles should not be blank, it should be either USER or ADMIN.";

    public static final String USER_NOT_FOUND_EX_MESSAGE = "User not found for the given id";
    public static final String INVALID_ROLE_EX_MESSAGE = "Given role is invalid. Role should be either USER or ADMIN";

    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final Set<String> ROLES = Set.of(ROLE_ADMIN, ROLE_USER);

    private Constant() {
    }

}

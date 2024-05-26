package com.example.userservice;

import com.example.userservice.entity.RoleEntity;
import com.example.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.userservice.utils.Constant.ROLE_ADMIN;
import static com.example.userservice.utils.Constant.ROLE_USER;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        RoleEntity userRole = RoleEntity.builder()
                .role(ROLE_USER)
                .build();

        RoleEntity adminRole = RoleEntity.builder()
                .role(ROLE_ADMIN)
                .build();
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
    }
}

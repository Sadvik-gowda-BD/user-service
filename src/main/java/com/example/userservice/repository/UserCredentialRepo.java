package com.example.userservice.repository;

import com.example.userservice.entity.UserCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepo extends JpaRepository<UserCredentialEntity, Long> {
}

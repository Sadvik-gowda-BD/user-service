package com.example.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_DETAILS")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL_ID", nullable = false)
    private String emailId;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
}

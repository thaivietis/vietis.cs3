package com.nqt.cs3.domain;

import com.nqt.cs3.constant.GenderEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String fullName;
    String password;
    @Enumerated(EnumType.STRING)
    GenderEnum gender;
    String address;
    String phoneNumber;
    String email;
    String avatar;
    int age;
    Instant dateOfBirth;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
}

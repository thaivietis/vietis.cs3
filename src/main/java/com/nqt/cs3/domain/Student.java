package com.nqt.cs3.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nqt.cs3.constant.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String fullName;
    String password;
    @Enumerated(EnumType.STRING)
    GenderEnum gender;
    String address;
    String phoneNumber;
    String email;
    String avatar;
    LocalDate dateOfBirth;
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;

    @OneToMany(mappedBy = "student")
    List<Enrollment> enrollments;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.createdBy = "Nguyễn Quang Thái";
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
        this.updatedBy = "Nguyễn Quang Thái";
    }
}

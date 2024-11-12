package com.nqt.cs3.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nqt.cs3.anotation.FormatDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    long maxStudent;
    long quantityStudent;
    private double price;
    String image;
    String instructor;
    //    Thời gian bắt đầu
    @FormatDateTime(zoneId = "Asia/Ho_Chi_Minh")
    Instant dateStart;

    //    Thời gian kết thúc
    @FormatDateTime(zoneId = "Asia/Ho_Chi_Minh")
    Instant dateEnd;
    @FormatDateTime(zoneId = "Asia/Ho_Chi_Minh")
    Instant createdAt;

    @FormatDateTime(zoneId = "Asia/Ho_Chi_Minh")
    Instant updatedAt;
    String createdBy;
    String updatedBy;

    // @OneToMany(mappedBy = "course")
    // List<Enrollment> enrollments;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        createdBy = "Nguyễn Quang Thái";
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
        updatedBy = "Nguyễn Quang Thái";
    }
}

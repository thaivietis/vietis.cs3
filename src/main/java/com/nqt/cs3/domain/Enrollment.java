package com.nqt.cs3.domain;

import com.nqt.cs3.constant.StatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    // Thời gian đăng ký khóa học
    LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    @PrePersist
    public void prePersist() {
        enrollmentDate = LocalDate.now();
    }
}

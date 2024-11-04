package com.nqt.cs3.domain;

import com.nqt.cs3.constant.StatusEnum;
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
@Table(name = "enrollments")
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    //    Ngày đăng ký khóa học
    Instant enrollmentDate;

    @Enumerated(EnumType.STRING)
    StatusEnum status;


}

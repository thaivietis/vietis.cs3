package com.nqt.cs3.dto;

import java.time.LocalDate;

import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportWeekDTO {
    String nameCourse;
    Long studentRegistered;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate reportDate;

    @PrePersist
    public void prePersist() {
        this.reportDate = LocalDate.now();
    }
}

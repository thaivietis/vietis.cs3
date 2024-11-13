package com.nqt.cs3.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportDTO {
    String nameCourse;
    Long studentRegistered;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate reportDate;
}

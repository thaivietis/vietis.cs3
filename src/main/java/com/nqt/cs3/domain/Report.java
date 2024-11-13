package com.nqt.cs3.domain;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "reports")
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String nameReport;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate reportDate;
    String pathFile;
    String createdBy;

    @PrePersist
    public void prePersist() {
        this.reportDate = LocalDate.now();
        this.createdBy = "Hệ thống";
    }
}

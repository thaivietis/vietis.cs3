package com.nqt.cs3.repository;

import com.nqt.cs3.domain.Enrollment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByEnrollmentDateBetween(LocalDate startOfWeek, LocalDate endOfWeek);
}

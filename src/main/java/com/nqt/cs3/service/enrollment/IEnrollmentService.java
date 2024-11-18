package com.nqt.cs3.service.enrollment;

import com.nqt.cs3.domain.Enrollment;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEnrollmentService {
    Enrollment findById(long id);
    List<Enrollment> findAll();
    Enrollment save(Enrollment enrollment);
    Enrollment update(Enrollment enrollment);
    void deleteById(long id);
    Enrollment findByCourseIdAndUserId(long courseId, long userId);
    Page<Enrollment> filterEnrollmentByEmail(String email, Pageable pageable);
}

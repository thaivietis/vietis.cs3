package com.nqt.cs3.service.IService;

import com.nqt.cs3.domain.Enrollment;

import java.util.List;
import java.util.Optional;

public interface IEnrollmentService {
    Enrollment findById(long id);
    List<Enrollment> findAll();
    Enrollment save(Enrollment enrollment);
    Enrollment update(Enrollment enrollment);
    void delete(long id);
    Optional<Enrollment> findByCourseIdAndUserId(long courseId, long userId);
}

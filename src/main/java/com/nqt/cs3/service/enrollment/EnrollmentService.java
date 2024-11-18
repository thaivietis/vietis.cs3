package com.nqt.cs3.service.enrollment;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.repository.EnrollmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService implements IEnrollmentService {
    @Autowired
    public EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment findById(long id) {
        return this.enrollmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Enrollment> findAll() {
        return this.enrollmentRepository.findAll();
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return this.enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        Enrollment currentEnrollment = this.findById(enrollment.getId());
        if(currentEnrollment != null){
            currentEnrollment.setId(enrollment.getId());
            currentEnrollment.setEnrollmentDate(enrollment.getEnrollmentDate());
            currentEnrollment.setStatus(enrollment.getStatus());
            return this.enrollmentRepository.save(currentEnrollment);
        }
        return null;
    }

    @Override
    public void deleteById(long id) {
        this.enrollmentRepository.deleteById(id);
    }

    @Override
    public Enrollment findByCourseIdAndUserId(long courseId, long userId) {
        Optional<Enrollment> enrollment = this.enrollmentRepository.findByStudentIdAndCourseId(userId, courseId);
        if(!enrollment.isPresent()){
            return null;
        }
        return enrollment.get();
    }

    @Override
    public Page<Enrollment> filterEnrollmentByEmail(String email, Pageable pageable) {
        List<Enrollment> enrollments = this.findAll().stream()
                .filter(enrollment -> enrollment.getStudent().getEmail().equals(email))
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), enrollments.size());
        return new PageImpl<>(enrollments.subList(start, end), pageable, enrollments.size());
    }

}

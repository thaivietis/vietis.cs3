package com.nqt.cs3.service.enrollment;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.repository.EnrollmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}

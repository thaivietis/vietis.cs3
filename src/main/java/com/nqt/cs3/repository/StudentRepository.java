package com.nqt.cs3.repository;

import com.nqt.cs3.domain.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // boolean existsById(long id);
    boolean existsByEmail(String email);
    Student findByEmail(String email);
    List<Student> findAll();
}

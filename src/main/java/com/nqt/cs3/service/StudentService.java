package com.nqt.cs3.service;

import java.util.List;

import com.nqt.cs3.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqt.cs3.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // SAVE
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    // GET
    public Student findById(long id) {
        if (!studentRepository.findById(id).isPresent()) {
            return null;
        }
        return studentRepository.findById(id).get();
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    // UPDATE
    public String update(Student student) {
        return "";
    }

    // DELETE
    public void delete(long id) {
        studentRepository.deleteById(id);
    }
}

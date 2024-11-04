package com.nqt.cs3.service;

import java.util.List;
import java.util.Optional;

import com.nqt.cs3.domain.Student;
import com.nqt.cs3.service.IService.IStudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqt.cs3.repository.StudentRepository;

@Service
public class StudentService implements IStudentService {

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
    public Student update(Student student) {
        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        if (studentOptional.isPresent()) {
            Student currentStudent = studentOptional.get();
            currentStudent.setFullName(student.getFullName());
            currentStudent.setDateOfBirth(student.getDateOfBirth());
            currentStudent.setEmail(student.getEmail());
            currentStudent.setPhoneNumber(student.getPhoneNumber());
            currentStudent.setAddress(student.getAddress());
            currentStudent.setGender(student.getGender());
            currentStudent.setAvatar(student.getAvatar());
            return this.save(currentStudent);
        }
        return null;
    }

    // DELETE
    public void delete(long id) {
        studentRepository.deleteById(id);
    }
}

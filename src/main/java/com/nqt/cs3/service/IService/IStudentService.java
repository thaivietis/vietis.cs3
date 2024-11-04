package com.nqt.cs3.service.IService;

import com.nqt.cs3.domain.Student;

import java.util.List;

public interface IStudentService {
    Student save(Student student);
    Student findById(long id);
    List<Student> findAll();
    String update(Student student);
    void delete(long id);
}

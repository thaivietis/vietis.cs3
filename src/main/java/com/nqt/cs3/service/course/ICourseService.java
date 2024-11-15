package com.nqt.cs3.service.course;

import com.nqt.cs3.domain.Course;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICourseService {
    Course save(Course course);
    Course findById(long id);
    List<Course> findAllCourse();
    Page<Course> getCoursePage(Pageable pageable);
    void delete(long id);
    Course update(Course course);
}

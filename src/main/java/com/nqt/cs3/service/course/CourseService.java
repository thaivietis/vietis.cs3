package com.nqt.cs3.service.course;

import com.nqt.cs3.constant.GlobalConstant;
import com.nqt.cs3.domain.Course;
import com.nqt.cs3.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findById(long id) {
        return this.courseRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Course> getCoursePage(Pageable pageable) {
        return this.courseRepository.findAll(pageable);
    }

    @Override
    public Course update(Course course) {
        Course courseOptional = this.findById(course.getId());
        if (courseOptional != null) {
            Course currentCourse = new Course();
            currentCourse.setId(course.getId());
            currentCourse.setName(course.getName());
            currentCourse.setDescription(course.getDescription());
            currentCourse.setMaxStudent(course.getMaxStudent());
            currentCourse.setImage(course.getImage());
            currentCourse.setInstructor(course.getInstructor());
            currentCourse.setPrice(course.getPrice());
            currentCourse.setQuantityStudent(course.getQuantityStudent());
            return this.save(currentCourse);
        }
        return null;
    }

    @Override
    public void delete(long id) {
        this.courseRepository.deleteById(id);
    }

    public void updateQuantityStudentAfterEnrollmentCourse(long id) {
        Course currentCourse = this.findById(id);
        if (currentCourse != null && currentCourse.getQuantityStudent() <= currentCourse.getMaxStudent()) {
            currentCourse
                    .setQuantityStudent(currentCourse.getQuantityStudent() + GlobalConstant.A_STUDENT_CLICK_REGISTER);
            this.save(currentCourse);
        }
    }

    public boolean checkQuantityStudent(long id) {
        Course currentCourse = this.findById(id);
        return currentCourse.getQuantityStudent() < currentCourse.getMaxStudent();
    }

    @Override
    public List<Course> findAllCourse() {
        return this.courseRepository.findAll();
    }

    public Page<Course> filterBaseCourse(Pageable pageable) {
        List<Course> filteredCourses = this.findAllCourse().stream()
                .filter(course -> course.getPrice() == 0)
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredCourses.size());
        List<Course> pagedCourses = filteredCourses.subList(start, end);
        return new PageImpl<>(pagedCourses, pageable, filteredCourses.size());
    }

    public Page<Course> filterEnhanceCourse(Pageable pageable) {
        List<Course> filteredCourses = this.findAllCourse()
                .stream()
                .filter(course -> course.getPrice() > 0)
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredCourses.size());
        List<Course> pagedCourses = filteredCourses.subList(start, end);
        return new PageImpl<>(pagedCourses, pageable, filteredCourses.size());
    }
}

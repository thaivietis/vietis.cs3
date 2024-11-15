package com.nqt.cs3.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.nqt.cs3.constant.RoleEnum;
import com.nqt.cs3.domain.Course;
import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.domain.Student;
import com.nqt.cs3.dto.EmailReaderItemDTO;
import com.nqt.cs3.service.course.CourseService;
import com.nqt.cs3.service.enrollment.EnrollmentService;
import com.nqt.cs3.service.student.StudentService;

import jakarta.annotation.PostConstruct;

@Component
public class EmailItemReader implements ItemReader<EmailReaderItemDTO> {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private int nextIndex = 0;
    public EmailItemReader(EnrollmentService enrollmentService, CourseService courseService, StudentService studentService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.studentService = studentService;
    }
    private List<Course> notEnrolledCourses;
    private List<Course> courses;
    private List<Enrollment> enrollments;
    private List<Student> students;
    private List<EmailReaderItemDTO> emailReaderItemDTOList;

    @PostConstruct
    public void initObject() {
        courses = this.courseService.findAllCourse();
        enrollments = this.enrollmentService.findAll();
        students = this.studentService.findAll();
        emailReaderItemDTOList = new ArrayList<>();
        for(Student student: students) {
            if (student.getRole().getName().equals(RoleEnum.ADMIN)) {
                continue;
            }else{
                Set<Long> enrolledCourseIds = enrollments.stream()
                .filter(enrollment -> enrollment.getStudent().getId() == student.getId())
                .map(enrollment -> enrollment.getCourse().getId())
                .collect(Collectors.toSet());

                notEnrolledCourses = courses.stream()
                    .filter(course -> !enrolledCourseIds.contains(course.getId()))
                    .collect(Collectors.toList());
                EmailReaderItemDTO emailReaderItemDTO = new EmailReaderItemDTO(student.getEmail(), notEnrolledCourses);
                emailReaderItemDTOList.add(emailReaderItemDTO);
            }
        }
    }

    @Override
    public EmailReaderItemDTO read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (nextIndex < emailReaderItemDTOList.size()) {
            return emailReaderItemDTOList.get(nextIndex++);
        }
        return null;
    }

}

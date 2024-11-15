package com.nqt.cs3.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nqt.cs3.constant.GlobalConstant;
import com.nqt.cs3.constant.RoleEnum;
import com.nqt.cs3.domain.Course;
import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.domain.Student;
import com.nqt.cs3.dto.RegisterDTO;
import com.nqt.cs3.service.course.CourseService;
import com.nqt.cs3.service.enrollment.EnrollmentService;
import com.nqt.cs3.service.student.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomepageController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;  
    
    @Autowired 
    private EnrollmentService enrollmentService;

    @GetMapping("/")
    public String homepage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // Kiểm tra xem page có  tồn tại không? Nếu có thì lấy giá trị và ép kiểu int
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {}
        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Course> prs = this.courseService.getCoursePage(pageable);
        List<Course> courses = prs.getContent();
        List<Course> advancedCourses = this.courseService.filterEnhanceCourse();
        List<Course> freeCourses = this.courseService.filterBaseCourse();
        model.addAttribute("advancedCourses", advancedCourses);
        model.addAttribute("freeCourses", freeCourses);
        model.addAttribute("totalPages", prs.getTotalPages());
        model.addAttribute("currentPage", page);
        return "client/index";
    }

    // Enrollment
    @GetMapping("/enrollment-course")
    public String enrollmentCourse() {
        return "client/enrollmented_course";
    }

    // Enrollment
    @GetMapping("/detail-course/{id}")
    public String getEnrollmentCourseById(@PathVariable("id") long id, Model model) {
        Course course = this.courseService.findById(id);
        Student student = this.studentService.findByEmail(this.studentService.getUserNameInContextHolder());
        Enrollment checkEnrollment = this.enrollmentService.findByCourseIdAndUserId(course.getId(), student.getId());
        boolean checkMaxStudent = this.courseService.checkQuantityStudent(id);
        model.addAttribute("course", course);
        model.addAttribute("checkEnrollment", checkEnrollment);
        model.addAttribute("checkMaxStudent", checkMaxStudent);
        return "client/detail_course";
    }

    @PostMapping("/detail-course")
    public String postEnrollmentCourseById(@RequestParam("id") long id) {
        String email = this.studentService.getUserNameInContextHolder();
        this.courseService.updateQuantityStudentAfterEnrollmentCourse(id);
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(this.courseService.findById(id));
        enrollment.setStudent(this.studentService.findByEmail(email));
        this.enrollmentService.save(enrollment);
        return "redirect:/";
    }

    @GetMapping("/enrollmented-course")
    public String getAllEnrollmentCourse(Model model) {
        List<Enrollment> enrollments = this.enrollmentService.findAll();
        String email = this.studentService.getUserNameInContextHolder();
        List<Enrollment> enrollmentStream = enrollments.stream().filter(enrollment -> enrollment.getStudent().getEmail().equals(email)).collect(Collectors.toList());
        model.addAttribute("enrollments", enrollmentStream);
        return "client/enrollmented_course";
    }
    
    @GetMapping("/enrollment-id")
    public String getDeleteEnrollmentedCourse(@RequestParam("id") long id) {
        Enrollment enrollment = this.enrollmentService.findById(id);
        Course course = this.courseService.findById(enrollment.getCourse().getId());
        course.setQuantityStudent(course.getQuantityStudent() - GlobalConstant.A_STUDENT_CLICK_CANCAL_COURSE);
        this.enrollmentService.deleteById(id);
        return "redirect:/enrollmented-course";
    }
}

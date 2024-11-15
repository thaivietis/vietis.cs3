package com.nqt.cs3.controller;

import com.nqt.cs3.constant.GlobalConstant;
import com.nqt.cs3.domain.Course;
import com.nqt.cs3.service.course.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course")
    public String getAllCourse(Model model, @RequestParam("page") Optional<String> pageOptional){
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {}
        Pageable pageable = PageRequest.of(page - 1, GlobalConstant.QUANTITY_RECORD_COURSE);
        Page<Course> courses = this.courseService.getCoursePage(pageable);
        model.addAttribute("courses", courses.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", courses.getTotalPages());
        return "course/show";
    }

    @GetMapping("/course/create")
    public String getCreateCourse(Model model){
        model.addAttribute("newCourse", new Course());
        return "course/create";
    }
    
    @PostMapping("/course/create")
    public String postCreateCourse(@ModelAttribute("newCourse") Course course){
        this.courseService.save(course);
        return "redirect:/course";
    }

    @GetMapping("/course/detail/{id}")
    public String getDetailCourse(@PathVariable("id") long id, Model model){
        Course course = this.courseService.findById(id);
        model.addAttribute("course", course);
        return "course/detail";
    }

    @GetMapping("/course/update/{id}")
    public String getUpdateCourse(@PathVariable("id") long id, Model model){
        Course currentCourse = this.courseService.findById(id);
        model.addAttribute("currentCourse", currentCourse);
        return "course/update";
    }

    @PostMapping("/course/update")
    public String postUpdateCourse(@ModelAttribute("currentCourse") Course course){
        this.courseService.update(course);
        return "redirect:/course";
    }

    @GetMapping("/course/delete/{id}")
    public String getDeleteCourse(@PathVariable("id") long id, Model model){
        Course course = this.courseService.findById(id);
        model.addAttribute("course", course);
        return "course/delete";
    }

    @PostMapping("/course/delete")
    public String postDeleteCourse(@ModelAttribute("course") Course course){
        this.courseService.delete(course.getId());
        return "redirect:/course";
    }
}

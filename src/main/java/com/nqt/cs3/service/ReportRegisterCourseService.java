package com.nqt.cs3.service;

import org.springframework.batch.item.ItemProcessor;

import com.nqt.cs3.domain.Enrollment;
import com.nqt.cs3.dto.ReportRegisterCourseDTO;

public class ReportRegisterCourseService implements ItemProcessor<Enrollment, ReportRegisterCourseDTO>{

    @Override
    public ReportRegisterCourseDTO process(Enrollment item) throws Exception {
        ReportRegisterCourseDTO reportRegisterCourseDTO = new ReportRegisterCourseDTO();
        reportRegisterCourseDTO.setNameCourse(item.getCourse().getName());
        reportRegisterCourseDTO.setQuantityStudent(item.getCourse().getQuantityStudent());
        return reportRegisterCourseDTO;
    }
    
}

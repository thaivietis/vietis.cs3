package com.nqt.cs3.service;

import org.springframework.batch.item.ItemProcessor;

import com.nqt.cs3.dto.ReportRegisterCourseDTO;

public class ReportRegisterCourseService implements ItemProcessor<ReportRegisterCourseDTO, ReportRegisterCourseDTO> {

    @Override
    public ReportRegisterCourseDTO process(ReportRegisterCourseDTO item) throws Exception {
        ReportRegisterCourseDTO reportRegisterCourseDTO = new ReportRegisterCourseDTO();
        reportRegisterCourseDTO.setNameCourse(item.getNameCourse());
        reportRegisterCourseDTO.setStudentRegistered(item.getStudentRegistered());
        return reportRegisterCourseDTO;
    }

}

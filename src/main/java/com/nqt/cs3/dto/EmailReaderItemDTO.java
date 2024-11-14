package com.nqt.cs3.dto;

import java.util.List;

import com.nqt.cs3.domain.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class EmailReaderItemDTO {
    String email;
    List<Course> courses;
}

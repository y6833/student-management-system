package com.yangy.entity;

import lombok.Data;

import java.util.List;

@Data
public class ExaminationDTO {
    private Examination examination;
    private List<String> courses;
}

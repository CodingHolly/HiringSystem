package com.holly.back_end.entity;

import lombok.Data;

/**
 * 用户简历
 * */
@Data
public class Resume {
    private Integer id;
    private String phone;
    private String personalAdvantage;
    private String expectedPosition;
    private String expectedSalary;
    private String expectedWorkCity;
    private String workExperience;
    private String projectExperience;
    private String educationExperience;
}

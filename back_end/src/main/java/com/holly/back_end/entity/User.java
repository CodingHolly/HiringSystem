package com.holly.back_end.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User extends Account {
    private Integer sex;
    private String jobSearchStatus;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    private String identity;
    private String email;
    private String birthplace;
}

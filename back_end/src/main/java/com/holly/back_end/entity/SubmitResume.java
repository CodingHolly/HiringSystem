package com.holly.back_end.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SubmitResume {
    private Integer id;
    private String userPhone;
    private String hrPhone;
    private Integer positionId;
    private String comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;
    private String companyName;
    private String positionName;
}

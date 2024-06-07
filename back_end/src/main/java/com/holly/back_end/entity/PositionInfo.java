package com.holly.back_end.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PositionInfo {
    private Integer id;
    private String positionName;
    private String companyName;
    private String category;
    private String type;
    private String salary;
    private Integer number;
    private String workAddress;
    private String requirement;
    private String profile;
    private String welfare;
    private String principal;
    private String isReleased;
    private String lastEditor;
    private String releasePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;
}

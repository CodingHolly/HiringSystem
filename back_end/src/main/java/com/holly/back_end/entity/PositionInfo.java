package com.holly.back_end.entity;

import lombok.Data;

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
}

package com.holly.back_end.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 客服后台管理
 * 公告信息
 * */
@Data
public class Announcement {
    private Integer id;
    private String aid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
    private String name;
    private String title;
    private String content;


}

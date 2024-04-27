package com.holly.back_end.entity;

import lombok.Data;

/**
 * 客服后台管理
 * 职位分类表
 */
@Data
public class PositionType {
    /**
     * ID
     */
    private Integer id;
    /**
     * 一级分类
     */
    private String category;
    /**
     * 二级分类
     */
    private String type;
    /**
     * 分类描述
     */
    private String description;

}

package com.holly.back_end.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 客服后台管理
 * 企业后台管理
 * 企业信息表
 * */
@Data
public class CompanyInfo {
    private Integer id;
    private String companyName; // 公司名称
    private String companyProfile; // 公司简介
    private String legalRepresentative; // 法定代表人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date foundingTime; // 成立时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime; // 网站注册时间
    private boolean managementStatus; // 经营状态，1：在营，0：停业
    private String companyAddress;  //公司地址
    private String companyIndustry; // 公司行业/领域类别
    private String registerCapital; // 注册资本
}

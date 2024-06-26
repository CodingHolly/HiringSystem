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
public class CompanyInfo{
    private Integer id;
    private String companyName; // 公司名称
    private String companyProfile; // 公司简介
    private String legalRepresentative; // 法定代表人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date foundingTime; // 成立时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime; // 网站注册时间
    private String managementStatus; // 经营状态
    private String companyCategory; // 公司行业/领域类别
    private String registeredCapital; // 注册资本
    private String registerAddress;  // 注册地址
    private String logo; // 企业logo
    private String companyScale; // 人数规模
}

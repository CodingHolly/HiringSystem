package com.holly.back_end.controller.request;

import lombok.Data;

import java.util.Date;
@Data
public class CompanyInfoPageRequest extends BaseRequest{
    private String companyName;
    private String companyAddress;
    private String companyCategory;

}

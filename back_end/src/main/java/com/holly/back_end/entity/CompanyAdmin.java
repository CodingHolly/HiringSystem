package com.holly.back_end.entity;

import lombok.Data;

@Data
public class CompanyAdmin extends Account{
    private String companyName;
    private String positionCategory;
    private String positionType;
    private String email;
    private String avatar;
}

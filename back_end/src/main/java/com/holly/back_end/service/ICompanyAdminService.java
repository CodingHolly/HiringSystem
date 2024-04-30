package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.CompanyAdmin;
import com.holly.back_end.entity.PositionType;

import java.util.List;

public interface ICompanyAdminService {
    CompanyAdmin login(Account account);

    CompanyAdmin getById(Integer id);

    PageInfo<CompanyAdmin> page(BaseRequest baseRequest);

    void save(CompanyAdmin companyAdmin);

    void deleteById(Integer id);

}

package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.entity.CompanyInfoAddress;

import java.util.List;

public interface ICompanyInfoService {
//    void add(CompanyInfo companyInfo);

    PageInfo<CompanyInfo> page(BaseRequest baseRequest);

    List<CompanyInfoAddress> getAddressByCompanyName(String companyName);

    List<CompanyInfo> getAllCompanyCategories();

    CompanyInfo getAllCompanyInfoByCompanyName(String companyName);

    void saveInfo(CompanyInfo companyInfo);

    void saveAddress(CompanyInfoAddress companyInfoAddress);

    void deleteAddressById(Integer id);
}

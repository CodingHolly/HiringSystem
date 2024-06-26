package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.entity.CompanyInfoAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyInfoMapper {

    List<CompanyInfo> listByCondition(BaseRequest baseRequest);

    List<CompanyInfoAddress> getAddressByCompanyName(String companyName);

    List<CompanyInfo> getAllCompanyCategories();

    CompanyInfo getAllCompanyInfoByCompanyName(String companyName);

    void saveInfo(CompanyInfo companyInfo);

    void insertAddress(CompanyInfoAddress companyInfoAddress);

    void updateAddress(CompanyInfoAddress companyInfoAddress);

    void deleteAddressById(Integer id);

    void insert(CompanyInfo dbCompanyInfo);

    String getLogoByName(String companyName);
}

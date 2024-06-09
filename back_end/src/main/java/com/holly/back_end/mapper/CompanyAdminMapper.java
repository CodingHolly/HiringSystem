package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.CompanyAdmin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyAdminMapper {
    CompanyAdmin getByPhone(String phone);

    CompanyAdmin getById(Integer id);

    List<CompanyAdmin> listByCondition(BaseRequest baseRequest);

    void update(CompanyAdmin companyAdmin);

    void deleteById(Integer id);

    void insert(CompanyAdmin dbCompanyAdmin);

    List<CompanyAdmin> getByCompanyName(String companyName);

    CompanyAdmin getAdminByUsername(String username);
}

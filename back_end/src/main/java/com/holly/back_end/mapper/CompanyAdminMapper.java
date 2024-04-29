package com.holly.back_end.mapper;

import com.holly.back_end.entity.CompanyAdmin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyAdminMapper {
    CompanyAdmin getByPhone(String phone);
}

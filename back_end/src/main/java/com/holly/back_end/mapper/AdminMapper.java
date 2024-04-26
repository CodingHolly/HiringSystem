package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.LoginRequest;
import com.holly.back_end.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    //@Select("select * from announcement")
    List<Admin> list();

    List<Admin> listByCondition(BaseRequest basePageRequest);

    Admin getById(Integer id);

    void insert(Admin admin);

    void update(Admin admin);

    void deleteById(Integer id);

    Admin getByPhoneAndPassword(LoginRequest loginRequest);
}

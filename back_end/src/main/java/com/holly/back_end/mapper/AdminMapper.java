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

    // 根据手机号和密码查询，用于用户登录认证
    Admin getByPhoneAndPassword(LoginRequest loginRequest);

    // 根据手机号查询，用于确定手机号唯一
    Admin getByPhone(String phone);
}

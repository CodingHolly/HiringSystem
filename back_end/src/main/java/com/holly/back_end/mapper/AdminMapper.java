package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.AnnouncementPageRequest;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.LoginRequest;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    //@Select("select * from announcement")
    List<Admin> list();

    List<Admin> listByCondition(BaseRequest basePageRequest);

    Admin getById(String id);

    void insert(Admin admin);

    void update(Admin admin);

    void deleteById(Integer id);

    Admin getByUsernameAndPassword(LoginRequest loginRequest);
}

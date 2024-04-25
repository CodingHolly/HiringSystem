package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.dto.LoginDTO;
import com.holly.back_end.controller.request.AnnouncementPageRequest;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.LoginRequest;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.entity.Announcement;

import java.util.List;

public interface IAdminService {
    //展示公告
    List<Admin> list();

    PageInfo<Admin> page(BaseRequest baseRequest);

    Admin getById(String id);

    void save(Admin admin);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest loginRequest);
}

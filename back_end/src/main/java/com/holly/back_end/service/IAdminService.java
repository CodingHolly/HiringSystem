package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.Admin;

import java.util.List;

public interface IAdminService {
    //展示公告
    List<Admin> list();

    PageInfo<Admin> page(BaseRequest baseRequest);

    Admin getById(Integer id);

    void save(Admin admin);

    void deleteById(Integer id);

    Admin login(Account account);

    void register(Account account);

    void upgrade(Integer id);
}

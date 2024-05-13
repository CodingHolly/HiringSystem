package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.UserPageRequest;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.User;

import java.util.List;

public interface IUserService {
    User login(Account account);

    User getById(Integer id);

   void register(Account account);

    void saveInfo(User user);

    PageInfo<User> page(BaseRequest baseRequest);
}

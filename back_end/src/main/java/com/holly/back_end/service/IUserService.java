package com.holly.back_end.service;

import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.User;

public interface IUserService {
    User login(Account account);

    User getById(Integer id);

   void register(Account account);
}

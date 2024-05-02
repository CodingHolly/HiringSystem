package com.holly.back_end.service.impl;

import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.CompanyAdmin;
import com.holly.back_end.entity.User;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.UserMapper;
import com.holly.back_end.service.IUserService;
import com.holly.back_end.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 登录
     */
    @Override
    public User login(Account account) {
        User dbUser = null;

        //判断数据库中是否存在该手机号用户
        try {
            dbUser = userMapper.getByPhone(account.getPhone());
        } catch (Exception e) {
            log.error("根据手机号{} 查询出错", account.getPhone());
            throw new ServiceException("手机号错误");
        }

        // 判断用户是否存在
        if (dbUser == null) {
            throw new ServiceException("用户不存在，请检查账号和密码");
        }

        // 判断密码是否正确
        String currentPassword = account.getPassword();
        if (!currentPassword.equals(dbUser.getPassword())) {
            throw new ServiceException("手机号或密码错误");
        }

        // 生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.genToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    @Override
    public User getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public void register(Account account) {
        User dbUser = userMapper.getByPhone(account.getPhone());

        //判断数据库中是否有该手机号的用户
        if (dbUser == null) {
            dbUser = new User();
            dbUser.setUsername(account.getUsername());
            dbUser.setPassword(account.getPassword());
            dbUser.setPhone(account.getPhone());
            dbUser.setRole("USER");
            userMapper.insert(dbUser);
        } else {
            throw new ServiceException("该手机号已被注册");
        }
        //生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.genToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
    }
}

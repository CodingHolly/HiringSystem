package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.PasswordRequest;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.User;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.UserMapper;
import com.holly.back_end.service.IUserService;
import com.holly.back_end.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            dbUser.setIsVip("普通用户");
            userMapper.insert(dbUser);
        } else {
            throw new ServiceException("该手机号已被注册");
        }
        //生成token
        String tokenData = dbUser.getId() + "-" + RoleEnum.USER.name();
        String token = TokenUtils.genToken(tokenData, dbUser.getPassword());
        dbUser.setToken(token);
    }

    @Override
    public void saveInfo(User user) {
        // 若修改了电话号码，检查数据库中是否有相同电话号码的用户
        User dbUser = userMapper.getByPhone(user.getPhone());
        if(dbUser.getId().equals(user.getId())) { // 不存在
            userMapper.update(user);
        } else {
            throw new ServiceException("该手机号已被其他用户注册");
        }
    }

    @Override
    public PageInfo<User> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> users = userMapper.listByCondition(baseRequest);
        return new PageInfo<>(users);
    }

    @Override
    public void changePassword(PasswordRequest passwordRequest) {
        int result = userMapper.updatePassword(passwordRequest);
        if (result <= 0) {
            throw new ServiceException("修改密码失败");
        }
    }

    @Override
    public User getByPhone(String phone) {
        return userMapper.getByPhone(phone);
    }
}

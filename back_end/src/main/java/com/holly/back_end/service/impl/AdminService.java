// 系统管理员
package com.holly.back_end.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.entity.User;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.AdminMapper;
import com.holly.back_end.mapper.UserMapper;
import com.holly.back_end.service.IAdminService;
import com.holly.back_end.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminService implements IAdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;

    private static final String DEFAULT_PASSWORD = "666666";
    private static final String PASSWORD_SALT = "work happily";

    // 列表所有
    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    // 分页展示
    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //                                密码加密
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //  暂未实现，当修改管理员除密码外的任意信息，token失效，跳转到登录页面，手机号或密码错误
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private String securePassword(String password) {
        return SecureUtil.md5(password + PASSWORD_SALT);
    }

    // 新增或更新
    @Override
    public void save(Admin admin) {
        if (admin.getId() == null) {    //没有id，新增

            admin.setCreatetime(new Date());
            //设置角色
            admin.setRole("ADMIN");
            //设置默认密码666666
            if (StrUtil.isBlank(admin.getPassword())) {
                admin.setPassword(DEFAULT_PASSWORD);
            }
            //设置md5加密，加盐
            //admin.setPassword(securePassword(admin.getPassword()));
            try {
                adminMapper.insert(admin);
            } catch (DuplicateKeyException e) {
                log.error("数据插入失败， phone:{}", admin.getPhone());
                throw new ServiceException("该手机号已注册");
            }
        } else {    //否则编辑
            admin.setUpdatetime(new Date());
            // 若修改了手机号，判断数据库中是否存在该手机号的用户
            Admin currentAdmin = adminMapper.getByPhone(admin.getPhone());
            if (currentAdmin.getId().equals(admin.getId())) {  //不存在
                //admin.setPassword(securePassword(admin.getPassword()));
                adminMapper.update(admin);
            } else {    //若存在，则不能更新数据库，抛出错误
                throw new ServiceException("该手机号已注册");
            }
        }
    }

    //单个删除
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    // 登录
    // 根据手机号唯一标识
    @Override
    public Admin login(Account account) {
        Admin dbAdmin = null;

        // 判断数据库中是否存在该手机号用户
        try {
            dbAdmin = adminMapper.getByPhone(account.getPhone());
        } catch (Exception e) {
            log.error("根据手机号{} 查询出错", account.getPhone());
            throw new ServiceException("手机号错误");
        }

        // 判断用户是否存在
        if (dbAdmin == null) {
            throw new ServiceException("用户不存在，请检查账号和密码");
        }

        // 判断密码是否正确
        String currentPassword = account.getPassword();
        if (!currentPassword.equals(dbAdmin.getPassword())) {
            throw new ServiceException("手机号或密码错误");
        }
        //生成token
        String tokenData = dbAdmin.getId() + "-" + RoleEnum.ADMIN.name();
        String token = TokenUtils.genToken(tokenData, dbAdmin.getPassword());
        dbAdmin.setToken(token);
        return dbAdmin;
    }

    @Override
    public void register(Account account) {
        Admin dbAdmin = adminMapper.getByPhone(account.getPhone());

        //判断数据库中是否有该手机号的用户
        if (dbAdmin == null) {
            dbAdmin = new Admin();
            dbAdmin.setUsername(account.getUsername());
            dbAdmin.setPassword(account.getPassword());
            dbAdmin.setPhone(account.getPhone());
            dbAdmin.setRole("ADMIN");
            dbAdmin.setCreatetime(new Date());
            adminMapper.insert(dbAdmin);
        } else {
            throw new ServiceException("该手机号已被注册");
        }
        //生成token
        String tokenData = dbAdmin.getId() + "-" + RoleEnum.ADMIN.name();
        String token = TokenUtils.genToken(tokenData, dbAdmin.getPassword());
        dbAdmin.setToken(token);
    }

    @Override
    public void upgrade(Integer id) {
        User dbUser = userMapper.getById(id);
        dbUser.setIsVip("VIP用户");
        userMapper.update(dbUser);
    }

    //id查询
    @Override
    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }


}

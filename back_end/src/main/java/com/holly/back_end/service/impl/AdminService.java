// 系统管理员
package com.holly.back_end.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.dto.LoginDTO;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.LoginRequest;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.AdminMapper;
import com.holly.back_end.service.IAdminService;
import com.holly.back_end.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class AdminService implements IAdminService {
    @Autowired
    AdminMapper adminMapper;

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
            //设置uid
            //管理员10开头
            Date date = new Date(); //+yyyyMMdd
            admin.setCreatetime(new Date());
            Random rand = new Random();
            int randomNum = Math.abs(rand.nextInt(100000));
            String last = String.format("%05d", randomNum);
            admin.setUid("10" + DateUtil.format(date, "yyyyMMdd") + last);

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
            if (currentAdmin == null) {  //不存在
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
    public LoginDTO login(LoginRequest loginRequest) {
        loginRequest.setPassword(loginRequest.getPassword());
        Admin admin = null;

        // 判断数据库中是否存在该手机号用户
        try {
            admin = adminMapper.getByPhone(loginRequest.getPhone());
        } catch (Exception e) {
            log.error("根据手机号{} 查询出错", loginRequest.getPhone());
            throw new ServiceException("手机号错误");
        }

        // 判断用户是否存在
        if (admin == null) {
            throw new ServiceException("用户不存在，请检查账号和密码");
        }

        // 判断密码是否正确
        String currentPassword = loginRequest.getPassword();
        if (!currentPassword.equals(admin.getPassword())) {
            throw new ServiceException("手机号或密码错误");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);  // admin的属性赋值给loginDTO

        //生成token
        String token = TokenUtils.genToken(String.valueOf(admin.getId()), admin.getPassword()); // 用户密码为密钥
        loginDTO.setToken(token);
        return loginDTO;
    }

    //id查询
    @Override
    public Admin getById(Integer id) {
        Admin admin = adminMapper.getById(id);
        return admin;
    }


}

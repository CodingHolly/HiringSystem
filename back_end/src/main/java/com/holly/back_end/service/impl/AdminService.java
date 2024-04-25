package com.holly.back_end.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.dto.LoginDTO;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.LoginRequest;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.AdminMapper;
import com.holly.back_end.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class AdminService implements IAdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.list();
    }

    @Override
    public PageInfo<Admin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Admin> admins = adminMapper.listByCondition(baseRequest);
        return new PageInfo<>(admins);
    }

    @Override
    public void save(Admin admin) {
        if (admin.getId() == null) {    //没有id，新增
            //设置uid
            //管理员00开头
            Date date = new Date(); //+yyyyMMdd
            admin.setCreatetime(new Date());
            Random rand = new Random();
            int randomNum = Math.abs(rand.nextInt(1000000));
            String last = String.format("%06d", randomNum);
            admin.setUid("00" + DateUtil.format(date, "yyyyMMdd") + last);
            adminMapper.insert(admin);
        } else {    //否则编辑
            admin.setUpdatetime(new Date());
            adminMapper.update(admin);
        }
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public LoginDTO login(LoginRequest loginRequest) {
        Admin admin = adminMapper.getByUsernameAndPassword(loginRequest);
        if (admin == null) {
            throw new ServiceException("用户名或密码错误");
        }
        LoginDTO loginDTO = new LoginDTO();
        BeanUtils.copyProperties(admin, loginDTO);//admin的属性赋值给loginDTO
        return loginDTO;
    }

    @Override
    public Admin getById(String id) {
        Admin admin = adminMapper.getById(id);
        return admin;
    }

}
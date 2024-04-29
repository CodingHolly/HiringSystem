package com.holly.back_end.service.impl;

import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.entity.CompanyAdmin;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.CompanyAdminMapper;
import com.holly.back_end.service.ICompanyAdminService;
import com.holly.back_end.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyAdminService implements ICompanyAdminService {
    @Autowired
    CompanyAdminMapper companyAdminMapper;

    @Override
    public CompanyAdmin login(Account account) {
        CompanyAdmin dbCompanyAdmin = null;

        // 判断数据库中是否存在该手机号用户
        try {
            dbCompanyAdmin = companyAdminMapper.getByPhone(account.getPhone());
        } catch (Exception e) {
            log.error("根据手机号{} 查询出错", account.getPhone());
            throw new ServiceException("手机号错误");
        }

        // 判断用户是否存在
        if (dbCompanyAdmin == null) {
            throw new ServiceException("用户不存在，请检查账号和密码");
        }

        // 判断密码是否正确
        String currentPassword = account.getPassword();
        if (!currentPassword.equals(dbCompanyAdmin.getPassword())) {
            throw new ServiceException("手机号或密码错误");
        }
        //生成token
        String tokenData = dbCompanyAdmin.getId()+"-"+ RoleEnum.COMPANY.name();
        String token = TokenUtils.genToken(tokenData,dbCompanyAdmin.getPassword());
        dbCompanyAdmin.setToken(token);
        return dbCompanyAdmin;
    }

    @Override
    public CompanyAdmin getById(Integer id) {
        return companyAdminMapper.getById(id);
    }
}

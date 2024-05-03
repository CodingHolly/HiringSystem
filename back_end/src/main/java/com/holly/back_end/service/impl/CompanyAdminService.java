package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.CompanyAdmin;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.CompanyAdminMapper;
import com.holly.back_end.mapper.CompanyInfoMapper;
import com.holly.back_end.service.ICompanyAdminService;
import com.holly.back_end.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyAdminService implements ICompanyAdminService {
    @Autowired
    CompanyAdminMapper companyAdminMapper;
    @Autowired
    CompanyInfoMapper companyInfoMapper;

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
        String tokenData = dbCompanyAdmin.getId() + "-" + RoleEnum.COMPANY.name();
        String token = TokenUtils.genToken(tokenData, dbCompanyAdmin.getPassword());
        dbCompanyAdmin.setToken(token);
        return dbCompanyAdmin;
    }

    @Override
    public CompanyAdmin getById(Integer id) {
        return companyAdminMapper.getById(id);
    }

    @Override
    public PageInfo<CompanyAdmin> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<CompanyAdmin> companyAdmins = companyAdminMapper.listByCondition(baseRequest);
        return new PageInfo<>(companyAdmins);
    }

    @Override
    public void save(CompanyAdmin companyAdmin) {
        if (companyAdmin.getId() == null) {
            companyAdmin.setRole("COMPANY");
            try {
                companyAdminMapper.insert(companyAdmin);
            } catch (DuplicateKeyException e) {
                log.error("数据插入失败， phone:{}", companyAdmin.getPhone());
                throw new ServiceException("该手机号已注册");
            }
        } else { // 编辑信息
            // 若修改了手机号，判断数据库中是否存在该手机号的用户
            CompanyAdmin dbCompanyAdmin = companyAdminMapper.getByPhone(companyAdmin.getPhone());
            if (dbCompanyAdmin.getId().equals(companyAdmin.getId())) { // 不存在
                companyAdminMapper.update(companyAdmin);
            } else {
                throw new ServiceException("该手机号已注册");
            }
        }

    }

    @Override
    public void deleteById(Integer id) {
        companyAdminMapper.deleteById(id);
    }

    @Override
    public void register(CompanyAdmin companyAdmin) {
        CompanyAdmin dbCompanyAdmin = companyAdminMapper.getByPhone(companyAdmin.getPhone());
        CompanyInfo dbCompanyInfo = companyInfoMapper.getAllCompanyInfoByCompanyName(companyAdmin.getCompanyName());

        //判断数据库中是否有该手机号的用户,是否存在该公司
        if (dbCompanyAdmin == null && dbCompanyInfo == null) {
            dbCompanyAdmin = new CompanyAdmin();
            dbCompanyAdmin.setCompanyName(companyAdmin.getCompanyName());
            dbCompanyAdmin.setUsername(companyAdmin.getUsername());
            dbCompanyAdmin.setPassword(companyAdmin.getPassword());
            dbCompanyAdmin.setPhone(companyAdmin.getPhone());
            dbCompanyAdmin.setRole("COMPANY");
            companyAdminMapper.insert(dbCompanyAdmin);

            //创建公司
            dbCompanyInfo = new CompanyInfo();
            dbCompanyInfo.setCompanyName(companyAdmin.getCompanyName());
            companyInfoMapper.insert(dbCompanyInfo);
        } else {
            throw new ServiceException("该手机号已被注册");
        }
        //生成token
        String tokenData = dbCompanyAdmin.getId() + "-" + RoleEnum.COMPANY.name();
        String token = TokenUtils.genToken(tokenData, dbCompanyAdmin.getPassword());
        dbCompanyAdmin.setToken(token);
    }
}

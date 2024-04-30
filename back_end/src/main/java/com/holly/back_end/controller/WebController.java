package com.holly.back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.holly.back_end.common.Result;
import com.holly.back_end.entity.Account;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.service.IAdminService;
import com.holly.back_end.service.ICompanyAdminService;
import com.holly.back_end.service.IUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    IAdminService adminService;
    @Autowired
    ICompanyAdminService companyAdminService;
    @Autowired
    IUserService userService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if (StrUtil.isEmpty(account.getPhone()) || StrUtil.isEmpty(account.getPassword()) || StrUtil.isEmpty(account.getRole())) {
            throw  new ServiceException("登录信息不完整");
        }
        if(RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        }
        if(RoleEnum.COMPANY.name().equals(account.getRole())) {
            account = companyAdminService.login(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())){
            account = userService.login(account);
        }
        return Result.success(account);
    }
}

package com.holly.back_end.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.service.IAdminService;
import com.holly.back_end.service.ICompanyInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.logging.Logger;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    private static final String ERROR_CODE_401 = "401";
    //private static final Logger log = (Logger) LoggerFactory.getLogger(JwtInterceptor.class);

    @Autowired
    private IAdminService adminService;
//    @Autowired
//    private ICompanyService companyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String token = request.getHeader("token");
//        if (StrUtil.isBlank(token)) {
//            token = request.getParameter("token");
//        }
//
//        // 执行认证
//        if (StrUtil.isBlank(token)) {
//            throw new ServiceException(ERROR_CODE_401, "无token，请重新登录");
//        }
//        // 获取 token 中的adminId
//        Account account = null;
//        try {
//            //解析token获取存储的数据
//            String userRole = JWT.decode(token).getAudience().get(0);
//            String userId = userRole.split("-")[0];
//            String role = userRole.split("-")[1];
//            //根据id查询数据库
//            if(RoleEnum.ADMIN.name().equals(role)) {
//                account = adminService.getById(Integer.valueOf(userId));
//            }
//            if(RoleEnum.COMPANY.name().equals(role)) {
//                account =
//            }
//        } catch (Exception e) {
//            throw new ServiceException(ERROR_CODE_401, "无token，请重新登录");
//        }
//        if(account == null) {
//            throw new ServiceException("用户不存在");
//        }
//        try {
//            //用户密码加签验证
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
//            jwtVerifier.verify(token);
//        }catch (JWTVerificationException e) {
//            throw new ServiceException(ERROR_CODE_401, "无token，请重新登录");
//        }
        return true;
    }
}

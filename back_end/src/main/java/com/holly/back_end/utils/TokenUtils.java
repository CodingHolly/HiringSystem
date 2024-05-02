//JWT工具类TokenUtils.java
package com.holly.back_end.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.Admin;
import com.holly.back_end.enums.RoleEnum;
import com.holly.back_end.service.IAdminService;
import com.holly.back_end.service.ICompanyAdminService;
import com.holly.back_end.service.ICompanyInfoService;
import com.holly.back_end.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class TokenUtils {

    private static IAdminService staticAdminService;
    private static ICompanyAdminService staticCompanyAdminService;
    private static IUserService staticUserService;

    @Resource
    private IAdminService adminService;
    @Resource
    private ICompanyAdminService companyAdminService;
    @Resource
    private IUserService userService;


    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
        staticCompanyAdminService = companyAdminService;
        staticUserService = userService;
    }






    /**
     * 生成token
     *
     * @return
     */
    public static String genToken(String adminId, String sign) {
        return JWT.create().withAudience(adminId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2h后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     * /admin?token=xxxx
     */
    public static Account getCurrentUser() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                //解析token获取存储的数据
                String userRole = JWT.decode(token).getAudience().get(0);
                String userId = userRole.split("-")[0];
                String role = userRole.split("-")[1];
                if (RoleEnum.ADMIN.name().equals(role)) {
                    return staticAdminService.getById(Integer.valueOf(userId));
                } else if (RoleEnum.COMPANY.name().equals(role)) {
                    return staticCompanyAdminService.getById(Integer.valueOf(userId));
                } else if (RoleEnum.USER.name().equals(role)) {
                    return staticUserService.getById(Integer.valueOf(userId));
                }
            }
        } catch (Exception e) {
            log.error("获取当前登录信息失败", e);
            return null;
        }
        return new Account();
    }
}



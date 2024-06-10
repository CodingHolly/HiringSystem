package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.PasswordRequest;
import com.holly.back_end.entity.User;
import com.holly.back_end.entity.UserInterest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User getByPhone(String phone);

    User getByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    User getById(Integer id);

    void insert(User dbUser);

    void update(User user);

    List<User> listByCondition(BaseRequest baseRequest);

    int updatePassword(PasswordRequest passwordRequest);

    void interest(UserInterest userInterest);

    UserInterest getByPhoneAndInterestId(String phone, Integer interestId);
}

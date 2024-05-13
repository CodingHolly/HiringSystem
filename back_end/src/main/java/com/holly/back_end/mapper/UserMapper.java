package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User getByPhone(String phone);

    User getById(Integer id);

    void insert(User dbUser);

    void update(User user);

    List<User> listByCondition(BaseRequest baseRequest);
}

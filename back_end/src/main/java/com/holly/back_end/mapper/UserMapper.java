package com.holly.back_end.mapper;

import com.holly.back_end.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getByPhone(String phone);

    User getById(Integer id);
}
package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    //@Select("select * from announcement")
    List<Announcement> list();

    List<Announcement> listByCondition(BaseRequest baseRequest);

    Announcement getById(Integer id);

    void insert(Announcement announcement);

    void update(Announcement announcement);

    void deleteById(Integer id);
}

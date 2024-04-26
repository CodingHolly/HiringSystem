package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.AnnouncementPageRequest;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Announcement;

import java.util.List;

public interface IAnnouncementService {
    //展示公告
    List<Announcement> list();

    PageInfo<Announcement> page(BaseRequest baseRequest);

    Announcement getById(Integer id);

    void save(Announcement announcement);

    void deleteById(Integer id);
}

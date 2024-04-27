// 公告
package com.holly.back_end.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.Announcement;
import com.holly.back_end.mapper.AnnouncementMapper;
import com.holly.back_end.service.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnnouncementService implements IAnnouncementService {
    @Autowired
    AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> list() {
        return announcementMapper.list();
    }

    @Override
    public PageInfo<Announcement> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<Announcement> announcements = announcementMapper.listByCondition(baseRequest);
        return new PageInfo<>(announcements);
    }

    @Override
    public void save(Announcement announcement) {
        if (announcement.getId() == null) {     //没有id，新增
            Date date = new Date();
            announcement.setAid(DateUtil.format(date, "yyyyMMdd") + Math.abs(IdUtil.fastSimpleUUID().hashCode()));
            announcementMapper.insert(announcement);
        } else {    //否则编辑
            announcement.setDate(new Date());
            announcementMapper.update(announcement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        announcementMapper.deleteById(id);
    }

    @Override
    public Announcement getById(Integer id) {
        Announcement announcements = announcementMapper.getById(id);
        return announcements;
    }

}

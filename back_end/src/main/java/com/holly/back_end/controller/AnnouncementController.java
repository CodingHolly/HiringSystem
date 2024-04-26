package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.AnnouncementPageRequest;
import com.holly.back_end.entity.Announcement;
import com.holly.back_end.service.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    IAnnouncementService announcementService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Announcement announcement) {
        announcementService.save(announcement);
        return Result.success(announcement);
    }

    //单个删除
    @DeleteMapping("/delete/{id}")
    public Result delete (@PathVariable Integer id){
        announcementService.deleteById(id);
        return Result.success();
    }

    //id查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Announcement announcement = announcementService.getById(id);
        return Result.success(announcement);
    }

    //列表所有
    @GetMapping("/list")
    public Result list() {
        List<Announcement> announcements = announcementService.list();
        return Result.success(announcements);
    }

    //分页展示
    @GetMapping("/page")
    public Result page(AnnouncementPageRequest announcementPageRequest) {
        return Result.success(announcementService.page(announcementPageRequest));
    }
}

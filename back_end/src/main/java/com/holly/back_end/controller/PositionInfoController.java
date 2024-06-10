package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.PositionInfoPageRequest;
import com.holly.back_end.controller.request.ReleaseRequest;
import com.holly.back_end.entity.PositionInfo;
import com.holly.back_end.entity.UserInterest;
import com.holly.back_end.service.impl.PositionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position_info")
public class PositionInfoController {
    @Autowired
    PositionInfoService positionInfoService;

    @GetMapping("/page")
    public Result page(PositionInfoPageRequest positionInfoPageRequest) {
        return Result.success(positionInfoService.page(positionInfoPageRequest));
    }

    @PostMapping("/save")
    public Result save(@RequestBody PositionInfo positionInfo) {
        positionInfoService.save(positionInfo);
        return Result.success(positionInfo);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        positionInfoService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/release")
    public Result release(@RequestBody ReleaseRequest releaseRequest) {
        positionInfoService.release(releaseRequest);
        return Result.success();
    }

    @GetMapping("/selectTop6")
    public Result selectTop6() {
        List<PositionInfo> positionInfos = positionInfoService.selectTop6();
        return Result.success(positionInfos);
    }

    @GetMapping("/selectById")
    public Result selectById(@RequestParam Integer id) {
        PositionInfo positionInfo = positionInfoService.selectById(id);
        return Result.success(positionInfo);
    }

    @PostMapping("/interest")
    public Result interest(@RequestBody UserInterest userInterest) {
        positionInfoService.interest(userInterest);
        return Result.success();
    }

    @GetMapping("/selectByCategoryId")
    public Result selectByCategoryId(@RequestParam Integer id) {
        List<PositionInfo> positionInfos = positionInfoService.selectByCategoryId(id);
        return Result.success(positionInfos);
    }
}

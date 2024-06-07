package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.PositionInfoPageRequest;
import com.holly.back_end.entity.PositionInfo;
import com.holly.back_end.service.impl.PositionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

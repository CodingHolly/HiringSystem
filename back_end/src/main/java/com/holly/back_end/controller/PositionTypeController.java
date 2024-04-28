package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.PositionTypePageRequest;
import com.holly.back_end.entity.PositionType;
import com.holly.back_end.service.IPositionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position_type")
public class PositionTypeController {
    @Autowired
    IPositionTypeService positionTypeService;
    /**
     * 查询所有
     * */
    @GetMapping("/list")
    public Result list() {
        List<PositionType> positionTypes = positionTypeService.list();
        return Result.success(positionTypes);
    }
    /**
     * 页面展示
     * */
    @GetMapping("/page")
    public Result page(PositionTypePageRequest positionTypeRequest) {
        return Result.success(positionTypeService.page(positionTypeRequest));
    }
    /**
     *  新增或更新
     * */
    @PostMapping("/save")
    public Result save(@RequestBody PositionType positionType) {
        positionTypeService.save(positionType);
        return Result.success(positionType);
    }
    /**
     *  展示所有一级分类
     */
    @GetMapping("/listCategory")
    public Result listCategory() {
        List<PositionType> positionTypes = positionTypeService.listCategory();
        return Result.success(positionTypes);
    }

}

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
    @GetMapping("/list_category")
    public Result listCategory() {
        List<PositionType> positionCategories = positionTypeService.listCategory();
        return Result.success(positionCategories);
    }
    /**
     *  展示所有二级分类
     */
    @GetMapping("/list_type")
    public Result listType() {
        List<PositionType> positionTypes = positionTypeService.listType();
        return Result.success(positionTypes);
    }
}

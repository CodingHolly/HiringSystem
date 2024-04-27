package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.PositionTypeRequest;
import com.holly.back_end.entity.PositionType;

import java.util.List;

public interface IPositionTypeService {
    void save(PositionType positionType);

    List<PositionType> list();

    PageInfo<PositionType> page(BaseRequest baseRequest);
}

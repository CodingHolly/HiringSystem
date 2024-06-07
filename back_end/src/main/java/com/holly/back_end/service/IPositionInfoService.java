package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.PositionInfoPageRequest;
import com.holly.back_end.entity.PositionInfo;

public interface IPositionInfoService {
    PageInfo<PositionInfo> page(BaseRequest baseRequest);

    void save(PositionInfo positionInfo);

    void deleteById(Integer id);
}

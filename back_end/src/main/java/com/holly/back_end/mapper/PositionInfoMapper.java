package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.PositionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionInfoMapper {
    List<PositionInfo> listByCondition(BaseRequest baseRequest);

    void insert(PositionInfo positionInfo);

    void update(PositionInfo positionInfo);

    void deleteById(Integer id);
}

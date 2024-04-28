package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.PositionType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PositionTypeMapper {
    void insert(PositionType positionType);

    void update(PositionType positionType);

    List<PositionType> list();

    List<PositionType> listByCondition(BaseRequest baseRequest);

    List<PositionType> listCategory();
}

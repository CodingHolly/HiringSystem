package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.PositionType;
import com.holly.back_end.mapper.PositionTypeMapper;
import com.holly.back_end.service.IPositionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionTypeService implements IPositionTypeService {
    @Autowired
    PositionTypeMapper positionTypeMapper;

    @Override
    public void save(PositionType positionType) {
        if(positionType.getId() == null) {
            positionTypeMapper.insert(positionType);
        } else {
            positionTypeMapper.update(positionType);
        }
    }

    @Override
    public List<PositionType> list() {
        return positionTypeMapper.list();
    }

    @Override
    public PageInfo<PositionType> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<PositionType> positionTypes = positionTypeMapper.listByCondition(baseRequest);
        return new PageInfo<>(positionTypes);
    }

    @Override
    public List<PositionType> listCategory() {
        return positionTypeMapper.listCategory();
    }

    @Override
    public List<PositionType> listType() {
        return positionTypeMapper.listType();
    }
}

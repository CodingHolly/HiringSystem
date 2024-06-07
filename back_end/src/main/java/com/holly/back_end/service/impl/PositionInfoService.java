package com.holly.back_end.service.impl;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.PositionInfoPageRequest;
import com.holly.back_end.entity.Announcement;
import com.holly.back_end.entity.PositionInfo;
import com.holly.back_end.mapper.PositionInfoMapper;
import com.holly.back_end.service.IPositionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PositionInfoService implements IPositionInfoService {
    @Autowired
    PositionInfoMapper positionInfoMapper;

    public PageInfo<PositionInfo> page(BaseRequest baseRequest) {
        List<PositionInfo> positionInfos = positionInfoMapper.listByCondition(baseRequest);
        return new PageInfo<>(positionInfos);
    }

    @Override
    public void save(PositionInfo positionInfo) {
        if (positionInfo.getId() == null) {
            positionInfoMapper.insert(positionInfo);
        } else {
            positionInfoMapper.update(positionInfo);
        }
    }

    @Override
    public void deleteById(Integer id) {
     positionInfoMapper.deleteById(id);
    }
}

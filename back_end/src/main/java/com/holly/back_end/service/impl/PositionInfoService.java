package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.ReleaseRequest;
import com.holly.back_end.entity.PositionInfo;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.PositionInfoMapper;
import com.holly.back_end.service.IPositionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PositionInfoService implements IPositionInfoService {
    @Autowired
    PositionInfoMapper positionInfoMapper;

    public PageInfo<PositionInfo> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<PositionInfo> positionInfos = positionInfoMapper.listByCondition(baseRequest);
        return new PageInfo<>(positionInfos);
    }

    @Override
    public void save(PositionInfo positionInfo) {
        if (positionInfo.getId() == null) {
            positionInfo.setIsReleased("未发布");
            positionInfo.setJobStatus("招聘中");
            positionInfoMapper.insert(positionInfo);
        } else {
            positionInfoMapper.update(positionInfo);
        }
    }

    @Override
    public void deleteById(Integer id) {
        positionInfoMapper.deleteById(id);
    }

    @Override
    public void release(ReleaseRequest releaseRequest) {
        PositionInfo positionInfo = positionInfoMapper.getById(releaseRequest.getId());
        if (positionInfo.getIsReleased().equals("已发布")) {
            throw new ServiceException("该职位已发布");
        } else {
            positionInfo.setIsReleased("已发布");
            positionInfo.setReleasePerson(releaseRequest.getUsername());
            positionInfo.setReleaseTime(new Date());
            positionInfoMapper.update(positionInfo);
        }
    }

    @Override
    public List<PositionInfo> selectTop6() {
        return positionInfoMapper.selectTop6();
    }

    @Override
    public PositionInfo selectById(Integer id) {
        return positionInfoMapper.selectById(id);
    }
}

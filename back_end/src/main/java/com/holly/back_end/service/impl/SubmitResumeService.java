package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.SubmitResume;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.SubmitResumeMapper;
import com.holly.back_end.service.ISubmitResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SubmitResumeService implements ISubmitResumeService {
    @Autowired
    SubmitResumeMapper submitResumeMapper;

    @Override
    public void submit(SubmitResume submitResume) {
        if (submitResumeMapper.getByUserPhoneAndPositionId(submitResume.getUserPhone(), submitResume.getPositionId()) == null) {
            submitResume.setSubmitTime(new Date());
            submitResumeMapper.insert(submitResume);
        } else {
            throw new ServiceException("该岗位已投递简历，请勿重复投递");
        }
    }

    @Override
    public PageInfo<SubmitResume> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<SubmitResume> submitResumes = submitResumeMapper.listAll(baseRequest);
        return new PageInfo<>(submitResumes);
    }

    @Override
    public PageInfo<SubmitResume> companyPage(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<SubmitResume> submitResumes = submitResumeMapper.listThisCompany(baseRequest);
        return new PageInfo<>(submitResumes);
    }

    @Override
    public SubmitResume selectById(Integer id) {
        return submitResumeMapper.selectById(id);
    }

    @Override
    public void saveComment(SubmitResume submitResume) {
        submitResume.setCommentTime(new Date());
        submitResumeMapper.update(submitResume);
    }
}

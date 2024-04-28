package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.entity.CompanyInfoAddress;
import com.holly.back_end.mapper.CompanyInfoMapper;
import com.holly.back_end.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyInfoService implements ICompanyInfoService {
    @Autowired
    CompanyInfoMapper companyInfoMapper;

//    /**
//     * 新增
//     * */
//    @Override
//    public void add(CompanyInfo companyInfo) {
//        CompanyInfo dbCompanyInfo = companyInfoMapper.selectByCompanyName(companyInfo.getCompanyName());
//        if(dbCompanyInfo != null) {
//            throw new ServiceException("该公司已存在");
//        }
//        companyInfo.setRegisterTime(new Date());
//        companyInfoMapper.insert(companyInfo);
//    }

    @Override
    public PageInfo<CompanyInfo> page(BaseRequest baseRequest) {
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<CompanyInfo> companyInfos = companyInfoMapper.listByCondition(baseRequest);
        return new PageInfo<>(companyInfos);
    }

    @Override
    public List<CompanyInfoAddress> getAddressByCompanyName(String companyName) {
        List<CompanyInfoAddress> companyInfoAddressList = companyInfoMapper.getAddressByCompanyName(companyName);
        return companyInfoAddressList;
    }
}

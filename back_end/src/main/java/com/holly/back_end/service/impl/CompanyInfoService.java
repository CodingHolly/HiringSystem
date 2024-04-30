package com.holly.back_end.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.entity.CompanyInfoAddress;
import com.holly.back_end.exception.ServiceException;
import com.holly.back_end.mapper.CompanyInfoMapper;
import com.holly.back_end.service.ICompanyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        return companyInfoMapper.getAddressByCompanyName(companyName);
    }

    @Override
    public List<CompanyInfo> getAllCompanyCategories() {
        return companyInfoMapper.getAllCompanyCategories();
    }

    @Override
    public CompanyInfo getAllCompanyInfoByCompanyName(String companyName) {
        return companyInfoMapper.getAllCompanyInfoByCompanyName(companyName);
    }

    @Override
    public void saveInfo(CompanyInfo companyInfo) {
        try {
            companyInfoMapper.saveInfo(companyInfo);
        } catch (DuplicateKeyException e) {
            log.error("数据插入失败， companyName:{}", companyInfo.getCompanyName());
            throw new ServiceException("该公司已注册");
        }
    }

    @Override
    public void saveAddress(CompanyInfoAddress companyInfoAddress) {
        if (companyInfoAddress.getId() == null) { // 新增地址
            companyInfoMapper.insertAddress(companyInfoAddress);
        } else { // 修改地址
            companyInfoMapper.updateAddress(companyInfoAddress);
        }
    }

    @Override
    public void deleteAddressById(Integer id) {
        companyInfoMapper.deleteAddressById(id);
    }

}

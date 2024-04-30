package com.holly.back_end.controller;

import cn.hutool.json.JSONArray;
import com.github.pagehelper.PageInfo;
import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.CompanyInfoPageRequest;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.entity.CompanyInfoAddress;
import com.holly.back_end.service.ICompanyInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company_info")
public class CompanyInfoController {
    @Autowired
    ICompanyInfoService companyInfoService;

    /**
     * 网站管理员端
     * 展示企业信息（request为空或传入查询条件）
     */
    @GetMapping("/page")
    public Result page(CompanyInfoPageRequest companyInfoPageRequest) {
        PageInfo<CompanyInfo> page = companyInfoService.page(companyInfoPageRequest);
        return Result.success(page);
    }

    /**
     * 网站管理员端
     * 企业用户端
     * 企业地址展示
     * 由企业名称返回企业多个地址
     */
    @GetMapping("/address/{companyName}")
    public Result getAddressByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CompanyInfoAddress> companyInfoAddresses = companyInfoService.getAddressByCompanyName(companyName);
        return Result.success(companyInfoAddresses);
    }

    /**
     * 网站管理员端
     * 查询所有企业类别
     */
    @GetMapping("/category")
    public Result getAllCompanyCategories() {
        List<CompanyInfo> companyInfoList = companyInfoService.getAllCompanyCategories();
        return Result.success(companyInfoList);
    }

    /**
     * 企业用户端
     * 由登录者所在企业名称查询该企业所有信息（不包括地址）
     */
    @GetMapping("/{companyName}")
    public Result getAllCompanyInfoByCompanyName(@PathVariable(value = "companyName") String companyName) {
        CompanyInfo companyInfo = companyInfoService.getAllCompanyInfoByCompanyName(companyName);
        return Result.success(companyInfo);
    }

    /**
     * 企业用户端
     * 保存企业修改信息（不包括地址信息）
     */
    @PostMapping("/save")
    public Result saveInfo(@RequestBody CompanyInfo companyInfo) {
        companyInfoService.saveInfo(companyInfo);
        return Result.success(companyInfo);
    }

    @PostMapping("/save_address")
    public Result saveAddress(@RequestBody CompanyInfoAddress companyInfoAddress) {
        companyInfoService.saveAddress(companyInfoAddress);
        return Result.success(companyInfoAddress);
    }

    @DeleteMapping("/delete_address/{id}")
    public Result deleteAddressById(@PathVariable Integer id) {
        companyInfoService.deleteAddressById(id);
        return Result.success();
    }
}

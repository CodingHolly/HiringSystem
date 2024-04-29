package com.holly.back_end.controller;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.CompanyInfoPageRequest;
import com.holly.back_end.entity.CompanyInfo;
import com.holly.back_end.entity.CompanyInfoAddress;
import com.holly.back_end.service.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/company_info")
public class CompanyInfoController {
    @Autowired
    ICompanyInfoService companyInfoService;

    @GetMapping("/page")
    public Result page(CompanyInfoPageRequest companyInfoPageRequest) {
        PageInfo<CompanyInfo> page = companyInfoService.page(companyInfoPageRequest);
        return Result.success(page);
    }

    @GetMapping("/address/{companyName}")
    public Result getAddressByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CompanyInfoAddress> companyInfoAddresses = companyInfoService.getAddressByCompanyName(companyName);
        return Result.success(companyInfoAddresses);
    }

    @GetMapping("/category")
    public Result getAllCompanyCategories() {
        List<CompanyInfo> companyInfoList = companyInfoService.getAllCompanyCategories();
        return Result.success(companyInfoList);
    }
}

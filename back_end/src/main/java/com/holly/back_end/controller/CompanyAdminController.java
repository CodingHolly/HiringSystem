package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.entity.CompanyAdmin;
import com.holly.back_end.service.impl.CompanyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyAdminController {
    @Autowired
    CompanyAdminService companyAdminService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        CompanyAdmin companyAdmin = companyAdminService.getById(id);
        return Result.success(companyAdmin);
    }
}

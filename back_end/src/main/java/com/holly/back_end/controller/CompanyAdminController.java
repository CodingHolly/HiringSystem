package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.CompanyAdminPageRequest;
import com.holly.back_end.entity.CompanyAdmin;
import com.holly.back_end.service.impl.CompanyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company_admin")
public class CompanyAdminController {
    @Autowired
    CompanyAdminService companyAdminService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        CompanyAdmin companyAdmin = companyAdminService.getById(id);
        return Result.success(companyAdmin);
    }

    @GetMapping("/page")
    public Result page(CompanyAdminPageRequest companyAdminPageRequest) {
        return Result.success(companyAdminService.page(companyAdminPageRequest));
    }

    @PostMapping("/save")
    public Result save(@RequestBody CompanyAdmin companyAdmin) {
        companyAdminService.save(companyAdmin);
        return Result.success(companyAdmin);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        companyAdminService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/admin/{companyName}")
    public Result getAdminsByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CompanyAdmin> companyAdmins = companyAdminService.getByCompanyName(companyName);
        return Result.success(companyAdmins);
    }

    @GetMapping("/position_info/{username}")
    public Result getAdminByUsername(@PathVariable (value = "username") String username) {
        CompanyAdmin companyAdmin = companyAdminService.getAdminByUsername(username);
        return Result.success(companyAdmin);

    }
}

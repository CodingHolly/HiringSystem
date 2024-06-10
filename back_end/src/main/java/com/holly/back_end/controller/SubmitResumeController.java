package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.HandleResumePageRequest;
import com.holly.back_end.controller.request.SubmitResumePageRequest;
import com.holly.back_end.entity.SubmitResume;
import com.holly.back_end.service.impl.SubmitResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit_resume")
public class SubmitResumeController {
    @Autowired
    SubmitResumeService submitResumeService;

    @PostMapping("/submit")
    public Result submit(@RequestBody SubmitResume submitResume) {
        submitResumeService.submit(submitResume);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(SubmitResumePageRequest submitResumePageRequest) {
        return Result.success(submitResumeService.page(submitResumePageRequest));
    }

    @GetMapping("/company_page")
    public Result companyPage(HandleResumePageRequest handleResumePageRequest) {
        return Result.success(submitResumeService.companyPage(handleResumePageRequest));
    }

}

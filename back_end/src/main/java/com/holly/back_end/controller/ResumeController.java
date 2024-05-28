package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.entity.Resume;
import com.holly.back_end.service.impl.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    ResumeService resumeService;

    @GetMapping("/{phone}")
    public Result getByPhone(@PathVariable(value = "phone") String phone) {
        Resume resume = resumeService.getByPhone(phone);
        return Result.success(resume);
    }

    @PostMapping("/save_resume")
    public Result saveResume(@RequestBody Resume resume) {
        resumeService.save(resume);
        return Result.success(resume);
    }
}

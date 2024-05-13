package com.holly.back_end.controller;

import com.holly.back_end.service.impl.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    ResumeService resumeService;
}

package com.holly.back_end.service.impl;

import com.holly.back_end.mapper.ResumeMapper;
import com.holly.back_end.service.IResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ResumeService implements IResumeService {
    @Autowired
    ResumeMapper resumeMapper;
}

package com.holly.back_end.service.impl;

import com.holly.back_end.entity.Resume;
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

    @Override
    public Resume getByPhone(String phone) {
        return resumeMapper.getByPhone(phone);
    }

    @Override
    public void save(Resume resume) {
        if (resume.getId() == null){
            resumeMapper.insert(resume);
        } else {
            resumeMapper.update(resume);
        }
    }
}

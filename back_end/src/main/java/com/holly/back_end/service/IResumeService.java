package com.holly.back_end.service;

import com.holly.back_end.entity.Resume;

public interface IResumeService {
    Resume getByPhone(String phone);

    void save(Resume resume);
}

package com.holly.back_end.mapper;

import com.holly.back_end.entity.Resume;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeMapper {
    Resume getByPhone(String phone);

    void insert(Resume resume);

    void update(Resume resume);
}

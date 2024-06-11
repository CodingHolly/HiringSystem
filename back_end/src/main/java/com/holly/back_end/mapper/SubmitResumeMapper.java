package com.holly.back_end.mapper;

import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.entity.SubmitResume;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubmitResumeMapper {
    SubmitResume getByUserPhoneAndPositionId(String userPhone, Integer positionId);

    void insert(SubmitResume submitResume);

    List<SubmitResume> listAll(BaseRequest baseRequest);

    List<SubmitResume> listThisCompany(BaseRequest baseRequest);

    SubmitResume selectById(Integer id);

    void update(SubmitResume submitResume);
}

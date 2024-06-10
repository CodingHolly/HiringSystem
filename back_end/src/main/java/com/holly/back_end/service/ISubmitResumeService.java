package com.holly.back_end.service;

import com.github.pagehelper.PageInfo;
import com.holly.back_end.controller.request.BaseRequest;
import com.holly.back_end.controller.request.HandleResumePageRequest;
import com.holly.back_end.entity.SubmitResume;

public interface ISubmitResumeService {
    void submit(SubmitResume submitResume);

    PageInfo<SubmitResume> page(BaseRequest baseRequest);

    PageInfo<SubmitResume> companyPage(BaseRequest baseRequest);
}

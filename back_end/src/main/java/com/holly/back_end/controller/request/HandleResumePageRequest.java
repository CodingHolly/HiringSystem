package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class HandleResumePageRequest extends BaseRequest{
    private String username;
    private String positionName;
    private String companyName;
    private String category;
    private String type;
}

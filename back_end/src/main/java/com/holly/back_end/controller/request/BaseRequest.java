package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class BaseRequest {
    private Integer pageNum = 1;
    private Integer pageSize = 8;
}

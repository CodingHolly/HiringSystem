package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class PositionTypeRequest extends BaseRequest{
    private String category;
    private String type;
    private String description;
}
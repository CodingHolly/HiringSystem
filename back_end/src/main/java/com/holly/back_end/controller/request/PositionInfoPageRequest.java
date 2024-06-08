package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class PositionInfoPageRequest extends BaseRequest{
    private String positionName;
    private String category;
    private String type;
    private String isReleased;
    private String isFull;
    private String companyName;
}

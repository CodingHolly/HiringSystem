package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class UserPageRequest extends BaseRequest{
    private String username;
    private String phone;
    private String email;
    private String sex;
    private String isVip;
}

package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class AdminPageRequest extends BaseRequest{
    private String uid;
    private String username;
    private String phone;
    private String email;

}

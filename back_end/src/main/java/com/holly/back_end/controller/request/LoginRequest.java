package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}

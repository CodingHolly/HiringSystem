package com.holly.back_end.controller.request;

import lombok.Data;

@Data
public class PasswordRequest {
    private String phone;
    private String username;
    private String password;
    private String newPassword;
}

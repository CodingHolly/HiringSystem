package com.holly.back_end.controller.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String phone;
    private String email;
    private String Token;
}

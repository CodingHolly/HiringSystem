package com.holly.back_end.entity;

import lombok.Data;

@Data
public class Account {
    private Integer id;
    private String name;
    private String phone;
    private String password;
    private String role;

}

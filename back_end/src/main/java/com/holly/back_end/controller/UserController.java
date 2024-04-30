package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }
}

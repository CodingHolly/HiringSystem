package com.holly.back_end.controller;

import com.holly.back_end.common.Result;
import com.holly.back_end.controller.request.PasswordRequest;
import com.holly.back_end.controller.request.UserPageRequest;
import com.holly.back_end.entity.User;
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

    @PostMapping("/save_info")
    public Result saveInfo(@RequestBody User user) {
        userService.saveInfo(user);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }

    @PostMapping("/password")
    public Result password(@RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(passwordRequest);
        return Result.success();
    }

    @GetMapping("/phone/{phone}")
    public Result getByPhone(@PathVariable(value = "phone") String phone) {
        User user = userService.getByPhone(phone);
        return Result.success(user);
    }
}

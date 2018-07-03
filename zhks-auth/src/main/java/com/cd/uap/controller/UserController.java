package com.cd.uap.controller;

import com.cd.uap.bean.Response;
import com.cd.uap.bean.ResultCode;
import com.cd.uap.bean.dto.RegistUserDto;
import com.cd.uap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by li.mingyang on 2018/4/20.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService appUserService;

    @PostMapping("/regist")
    public Response registUser(@RequestBody @Validated RegistUserDto registUserDto) throws Exception {
        appUserService.registUser(registUserDto);
        return new Response(0, ResultCode.SUCCESS, null);
    }

}

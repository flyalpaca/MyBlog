package com.yyj.blog.controller;


import com.yyj.blog.service.LoginService;
import com.yyj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
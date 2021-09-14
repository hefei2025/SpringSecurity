package com.hf.ssc.controller;

import com.hf.ssc.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller

public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/auth/form")
    @ResponseBody
    public String login(){
        return "Hello world!";
    }
    @RequestMapping("loginTest")
    public String init(){
        return "myLogin";
    }
}

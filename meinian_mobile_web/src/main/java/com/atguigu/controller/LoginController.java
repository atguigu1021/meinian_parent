package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map<String,Object> map, HttpResponse response){
        //注册用户

        //添加cookie到response，设置有效期30天

        return null;
    }







}

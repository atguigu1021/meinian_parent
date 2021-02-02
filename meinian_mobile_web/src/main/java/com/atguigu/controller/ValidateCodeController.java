package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.util.SMSUtils;
import com.atguigu.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result sendValidateCode(String phoneNumber) {
        //生成验证码
        String code = ValidateCodeUtils.generateValidateCode(4)+"";
        try {
            //验证码存储到redis中,五分钟有效
            jedisPool.getResource().setex(phoneNumber+ RedisMessageConstant.SENDTYPE_ORDER,300,code);
            //发送手机验证码
            SMSUtils.sendShortMessage(phoneNumber, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(telephone+RedisMessageConstant.SENDTYPE_LOGIN,code+"");
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}

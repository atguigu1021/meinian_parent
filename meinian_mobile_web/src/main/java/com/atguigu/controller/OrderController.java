package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/saveOrder")
    public Result saveOrder(@RequestBody Map<String, Object> map) {
        try {
            String telephone = (String) map.get("telephone");
            String validateCode = (String) map.get("validateCode");
            String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
            if (redisCode == null) {
                return new Result(false, "验证码已过期，请重新获取");
            } else if (!redisCode.equals(validateCode)) {
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
            return orderService.saveOrder(map);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ORDER_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) throws Exception {
        Map<String,Object> map=orderService.findById(id);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
    }

}

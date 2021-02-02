package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.apache.log4j.helpers.AbsoluteTimeDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void addBatch(List<OrderSetting> list) {
        for (OrderSetting orderSetting : list) {
           int count= orderSettingDao.getByDate(orderSetting.getOrderDate());
           if (count==0) {
               orderSettingDao.add(orderSetting);
           }else {
               orderSettingDao.update(orderSetting);
           }
        }
    }

    @Override
    public List<Map<String, Object>> getByMonth(String date) {
        String startDate=date+"-01";
        String endDate=date+"-31";
        List<OrderSetting> list = orderSettingDao.getByMonth(startDate, endDate);
        List<Map<String,Object>> result=new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String,Object> map=new HashMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            result.add(map);
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        int count= orderSettingDao.getByDate(orderSetting.getOrderDate());
        if (count==0) {
            orderSettingDao.add(orderSetting);
        }else {
            orderSettingDao.update(orderSetting);
        }
    }
}

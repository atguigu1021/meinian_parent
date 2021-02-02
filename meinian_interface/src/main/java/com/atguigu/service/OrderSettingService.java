package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void addBatch(List<OrderSetting> list);

    List<Map<String, Object>> getByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}

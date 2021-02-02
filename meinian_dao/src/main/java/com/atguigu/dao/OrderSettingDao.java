package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;
import com.sun.jdi.IntegerType;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingDao {


    void add(OrderSetting orderSetting);

    int getByDate(Date orderDate);

    void update(OrderSetting orderSetting);

    List<OrderSetting> getByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    OrderSetting getOrderSettingByDate(Date date);

    void updateReservations(@Param("date") Date date, @Param("reservations") Integer reservations);
}

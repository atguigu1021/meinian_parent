package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Menu;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.util.DateUtils;
import com.sun.tracing.dtrace.Attributes;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public Result saveOrder(Map<String, Object> map) throws Exception {
        Date date=DateUtils.parseString2Date(map.get("orderDate") + "");
        OrderSetting orderSetting = orderSettingDao.getOrderSettingByDate(date);
        Integer setMealId = Integer.parseInt(map.get("setMealId")+"");
        //判断当日是否可以预约
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        String telephone=map.get("telephone")+"";
        //判断member是否存在
        Member member = memberDao.getMember(telephone);
        if (member==null){
            member=new Member();
            member.setName(map.get("name")+"");
            member.setSex(map.get("sex")+"");
            member.setIdCard(map.get("idCard")+"");
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            //主键回填
            memberDao.add(member);
        }else {
            Integer id = member.getId();
            Order order=new Order();
            order.setOrderDate(DateUtils.parseString2Date(map.get("orderDate")+""));
            order.setMemberId(id);
            order.setSetmealId(setMealId);
            List<Order> list = orderDao.getOrder(order);
            if (list!=null && list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        //添加Order
        Order order=new Order();
        order.setMemberId(member.getId());
        order.setSetmealId(setMealId);
        order.setOrderDate(date);
        order.setOrderType(Order.ORDERTYPE_WEIXIN);
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        //主键回填
        orderDao.add(order);
        //设置已预约人数
        orderSettingDao.updateReservations(orderSetting.getOrderDate(),
                orderSetting.getReservations()+1);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map<String,Object> findById(Integer id) throws Exception {
        Map<String, Object> setMeal = orderDao.getOrderById(id);
        Date date = (Date) setMeal.get("orderDate");
        setMeal.put("orderDate",DateUtils.parseDate2String(date));
        return setMeal;
    }
}

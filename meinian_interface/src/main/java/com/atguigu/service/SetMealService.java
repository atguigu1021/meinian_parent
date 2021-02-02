package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {

    PageResult findPage(QueryPageBean queryPageBean);

    void add(Integer[] travelGroupIds, Setmeal setmeal);

    List<Setmeal> getSetMeal();

    Setmeal findById(Integer id);

    Setmeal getSetMealById(Integer id);

    List<Map<String, Object>> getSetMealReport();
}

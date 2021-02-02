package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    List<Setmeal> findPage(String queryString);

    void add(Setmeal setmeal);

    void addTravelGroupToSetMeal(Map<String, Integer> map);

    List<Setmeal> getSetMeal();

    Setmeal findById(Integer id);

    Setmeal getSetMealById(Integer id);

    List<Map<String, Object>> getSetMealReport();
}

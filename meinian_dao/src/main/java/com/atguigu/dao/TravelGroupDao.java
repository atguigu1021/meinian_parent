package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {


    void addTravelGroupAndTravelItem(Map<String, Integer> map);

    void add(TravelGroup travelGroup);

    List<TravelGroup> findPage(String queryString);

    TravelGroup getById(Integer id);

    List<Integer> getTravelItemsById(Integer id);

    void update(TravelGroup travelGroup);

    void delRelationship(Integer id);

    List<TravelGroup> findAll();

    List<TravelGroup> getOfSetMeal(Integer id);
}

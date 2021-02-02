package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TravelItemDao {

    void add(TravelItem travelItem);

    List<TravelItem> findPage(String queryString);

    void delete(Integer id);

    TravelItem getById(Integer id);

    void update(TravelItem travelItem);

    List<TravelItem> findAll();

    long findCountOfTravelItem(Integer id);


    List<TravelItem> getOfTravelGroup(Integer id);
}

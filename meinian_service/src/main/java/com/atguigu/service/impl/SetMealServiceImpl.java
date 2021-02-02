package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.SetMealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        PageInfo<Setmeal> pageInfo = new PageInfo<>(setMealDao.findPage(queryPageBean.getQueryString()));
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void add(Integer[] travelGroupIds, Setmeal setmeal) {
        setMealDao.add(setmeal);
        Integer id = setmeal.getId();
        setSetMealAndTravelGroup(travelGroupIds, id);


    }

    @Override
    public List<Setmeal> getSetMeal() {
        return setMealDao.getSetMeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public Setmeal getSetMealById(Integer id) {
        return setMealDao.getSetMealById(id);
    }

    @Override
    public List<Map<String, Object>> getSetMealReport() {

        return setMealDao.getSetMealReport();
    }

    private void setSetMealAndTravelGroup(Integer[] ids, Integer setMealId) {
        if (ids != null && ids.length > 0) {
            for (Integer id : ids) {
                Map<String, Integer> map = new HashMap<>();
                map.put("travelGroupId", id);
                map.put("setMealId", setMealId);
                setMealDao.addTravelGroupToSetMeal(map);
            }
        }
    }
}

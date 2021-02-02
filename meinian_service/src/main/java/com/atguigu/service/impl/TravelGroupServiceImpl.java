package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup);
        Integer id = travelGroup.getId();
       setTravelGroupAndTravelItem(travelItemIds,id);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        PageInfo<TravelGroup> pageInfo=new PageInfo<>(travelGroupDao.findPage(queryString));
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public TravelGroup getById(Integer id) {
        return travelGroupDao.getById(id);
    }

    @Override
    public List<Integer> getTravelItemsById(Integer id) {
        return travelGroupDao.getTravelItemsById(id);
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.update(travelGroup);
        Integer id = travelGroup.getId();
        travelGroupDao.delRelationship(id);
        setTravelGroupAndTravelItem(travelItemIds,id);
    }

    @Override
    public List<TravelGroup> finAll() {
        return travelGroupDao.findAll();
    }

    private void setTravelGroupAndTravelItem(Integer[] travelItemIds, Integer id){
        if (travelItemIds!=null && travelItemIds.length>0){
            Map<String,Integer> map=new HashMap<>();
            for (Integer travelItemId : travelItemIds) {
                map.put("travelGroupId",id);
                map.put("travelItemId",travelItemId);
                travelGroupDao.addTravelGroupAndTravelItem(map);
            }
        }
    }
}

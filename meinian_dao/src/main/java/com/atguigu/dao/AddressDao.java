package com.atguigu.dao;

import com.atguigu.pojo.Address;

import java.util.List;

public interface AddressDao {

    List<Address> findAllMaps();

    List<Address> findPage(String string);

    void add(Address address);

    void deleteById(Integer id);
}

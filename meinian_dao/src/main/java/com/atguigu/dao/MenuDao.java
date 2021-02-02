package com.atguigu.dao;

import com.atguigu.pojo.Menu;

import java.util.List;
import java.util.Set;

public interface MenuDao {

    Set<Menu> getMenusOfRole(Integer roleId);
}

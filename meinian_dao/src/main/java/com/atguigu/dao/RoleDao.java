package com.atguigu.dao;

import com.atguigu.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao {

    Set<Role> getRolesOfUser(Integer userId);
}

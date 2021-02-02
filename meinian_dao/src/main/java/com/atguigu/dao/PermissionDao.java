package com.atguigu.dao;

import com.atguigu.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {

    Set<Permission> getPermissionOfRole(Integer roleId);


}

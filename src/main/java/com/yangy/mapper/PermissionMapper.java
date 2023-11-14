package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {


    @Select("select action from tb_permission")
    List<Integer> getAllPermission();
}

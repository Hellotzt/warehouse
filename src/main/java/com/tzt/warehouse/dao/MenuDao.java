package com.tzt.warehouse.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzt.warehouse.entity.Menu;

import java.util.List;

public interface MenuDao extends BaseMapper<Menu> {
    List<String> selectParamsByUserId(String userId);
}

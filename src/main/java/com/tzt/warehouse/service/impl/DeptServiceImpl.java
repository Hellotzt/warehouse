package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.dao.DeptDao;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.service.DeptService;
import org.springframework.stereotype.Service;

/**
 * @author：帅气的汤
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {
    @Override
    public ResponseResult<Object> deptList(SearchDTO searchDTO) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("seq");
        Page<Dept> deptList = this.page(new Page<>(searchDTO.getCurrent(), searchDTO.getSize()),wrapper);
        return new ResponseResult<>(deptList);
    }
}

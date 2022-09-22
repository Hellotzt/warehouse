package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.mapper.DeptUserRoleDao;
import com.tzt.warehouse.entity.DeptUserRole;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.service.DeptUserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author：帅气的汤
 */
@Service
public class DeptUserRoleServiceImpl extends ServiceImpl<DeptUserRoleDao, DeptUserRole> implements DeptUserRoleService {
    @Override
    public ResponseResult<Object> deptList(SearchDTO searchDTO) {
        QueryWrapper<DeptUserRole> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("seq");
        Page<DeptUserRole> deptList = this.page(new Page<>(searchDTO.getCurrent(), searchDTO.getSize()),wrapper);
        return new ResponseResult<>(deptList);
    }
}

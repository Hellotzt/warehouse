package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.mapper.DeptDao;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.DeptUserRole;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.service.DeptService;
import com.tzt.warehouse.service.DeptUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author：帅气的汤
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {
    @Resource
    private DeptUserRoleService deptUserRoleService;

    @Override
    public ResponseResult<Object> deptList(SearchDTO searchDTO) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("seq");
        Page<Dept> deptList = this.page(new Page<>(searchDTO.getCurrent(), searchDTO.getSize()), wrapper);
        return new ResponseResult<>(deptList);
    }

    @Override
    public ResponseResult<Object> deleteDept(Dept dept) {
        if (dept.getId() == null) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        // 如果该部门有人，不允许删除
        DeptUserRole deptUserRole = deptUserRoleService.getOne(new QueryWrapper<DeptUserRole>().eq("dept_id", dept.getId()));
        if (deptUserRole != null) {
            return new ResponseResult<>(ErrorCodeEnum.DEPT_ERROR);
        }
        boolean b = this.removeById(dept.getId());
        if (!b) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        return ResponseResult.success();
    }
}

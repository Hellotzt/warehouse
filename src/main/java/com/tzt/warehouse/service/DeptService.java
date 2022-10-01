package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.dto.DeptDto;

/**
 * 部门service
 * @author 帅气的汤
 */
public interface DeptService extends IService<Dept> {
    /**
     * 部门列表
     * @param deptDto
     * @return
     */
    ResponseResult<Object> deptList(DeptDto deptDto);

    ResponseResult<Object> deleteDept(Dept dept);

    ResponseResult<Object> all();
}

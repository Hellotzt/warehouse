package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.DeptPosition;
import com.tzt.warehouse.entity.dto.DeptPositionDto;

/**
 * 部门职位表(DeptPosition)表服务接口
 *
 * @author tzt
 * @since 2022-09-25 02:36:07
 */
public interface DeptPositionService extends IService<DeptPosition> {
    /**
     * 部门添加职位
     */
    ResponseResult<Object> addDeptPosition(DeptPositionDto deptPositionDto);

    /**
     * 获取部门职位
     * @param deptPositionDto
     * @return
     */
    ResponseResult<Object> getDeptPosition(DeptPositionDto deptPositionDto);
}


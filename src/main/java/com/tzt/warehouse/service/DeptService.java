package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.dto.SearchDTO;

/**
 * 部门service
 * @author 帅气的汤
 */
public interface DeptService extends IService<Dept> {
    /**
     * 部门列表
     * @param searchDTO
     * @return
     */
    ResponseResult<Object> deptList(SearchDTO searchDTO);
}

package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.DeptUserRole;
import com.tzt.warehouse.entity.dto.SearchDTO;

/**
 *
 * @author 帅气的汤
 */
public interface DeptUserRoleService extends IService<DeptUserRole> {
    /**
     * 部门列表
     * @param searchDTO
     * @return
     */
    ResponseResult<Object> deptList(SearchDTO searchDTO);
}

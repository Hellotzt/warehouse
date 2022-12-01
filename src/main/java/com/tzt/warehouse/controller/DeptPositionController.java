package com.tzt.warehouse.controller;


import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.dto.DeptPositionDto;
import com.tzt.warehouse.service.DeptPositionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 部门权限表(DeptPosition)表控制层
 *
 * @author tzt
 * @since 2022-09-25 02:36:05
 */
@RestController
@RequestMapping("/deptPosition")
public class DeptPositionController {

    @Resource
    private DeptPositionService deptPositionService;

    /**
     * 部门绑定职位
     */
    @PostMapping("/addDeptPosition")
    public ResponseResult<Object> addDeptPosition(@RequestBody @Valid DeptPositionDto deptPositionDto) {
        return deptPositionService.addDeptPosition(deptPositionDto);
    }

    /**
     * 获取部门下的职位
     */
    @PostMapping("/getDeptPosition")
    public ResponseResult<Object> getDeptPosition(@RequestBody @Valid DeptPositionDto deptPositionDto) {
        return deptPositionService.getDeptPosition(deptPositionDto);
    }
}


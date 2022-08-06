package com.tzt.warehouse.controller;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.service.DeptService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author：帅气的汤
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @PostMapping("/list")
    public ResponseResult<Object> deptList(@RequestBody @Valid SearchDTO searchDTO) {
        return deptService.deptList(searchDTO);
    }

    @PostMapping("/add")
    public ResponseResult<Object> add(@RequestBody Dept dept) {
        if (!StringUtils.hasText(dept.getDeptName())) {
            return new ResponseResult<>(ErrorCodeEnum.ACCOUNT_NULL);
        }
        // deptName重复校验
        try {
            deptService.save(dept);
        } catch (Exception e) {
            return new ResponseResult<>(ErrorCodeEnum.PARAM_EXIST);
        }
        return ResponseResult.success();
    }

    @PostMapping("/delete")
    public ResponseResult<Object> delete(@RequestBody Dept dept) {
        return deptService.deleteDept(dept);
    }

    @PostMapping("/update")
    public ResponseResult<Object> update(@RequestBody @Valid Dept dept) {

        boolean b = deptService.updateById(dept);
        if (!b) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        return ResponseResult.success();
    }
}

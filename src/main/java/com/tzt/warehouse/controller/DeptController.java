package com.tzt.warehouse.controller;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.dto.DeptDto;
import com.tzt.warehouse.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author：帅气的汤
 */
@RestController
@RequestMapping("/dept")
@Slf4j
public class DeptController {
    @Resource
    private DeptService deptService;

    @PostMapping("/list")
    public ResponseResult<Object> deptList(@RequestBody DeptDto deptDto) {
        return deptService.deptList(deptDto);
    }

    @PostMapping("/all")
    public ResponseResult<Object> all() {
        return deptService.all();
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
            log.error("新增部门异常：{},操作用户：{}",e, UserUtlis.get().getUser().getUserName());
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

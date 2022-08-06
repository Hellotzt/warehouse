package com.tzt.warehouse.controller;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.DeptUserRole;
import com.tzt.warehouse.service.DeptUserRoleService;
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
@RequestMapping("/deptUserRole")
public class DeptUserRoleController {
    @Resource
    private DeptUserRoleService deptUserRoleService;

    // @PostMapping("/list")
    // public ResponseResult<Object> deptList(@RequestBody @Valid SearchDTO searchDTO) {
    //     return deptUserRoleService.deptList(searchDTO);
    // }

    @PostMapping("/add")
    public ResponseResult<Object> add(@RequestBody @Valid DeptUserRole deptUserRole) {
        // deptName重复校验
        try {
            deptUserRoleService.save(deptUserRole);
        } catch (Exception e) {
            return new ResponseResult<>(ErrorCodeEnum.PARAM_EXIST);
        }
        return ResponseResult.success();
    }

    // @PostMapping("/delete")
    // public ResponseResult<Object> delete(@RequestBody DeptUserRole deptUserRole) {
    //     if (deptUserRole.getId() == null) {
    //         return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
    //     }
    //     boolean b = deptUserRoleService.removeById(deptUserRole.getId());
    //     if (!b) {
    //         return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
    //     }
    //     return ResponseResult.success();
    // }
    //
    // @PostMapping("/update")
    // public ResponseResult<Object> update(@RequestBody @Valid DeptUserRole deptUserRole) {
    //     try {
    //         deptUserRoleService.updateById(deptUserRole);
    //     } catch (Exception e) {
    //         return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
    //     }
    //     return ResponseResult.success();
    // }
}

package com.tzt.warehouse.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author：帅气的汤
 * 职位表
 */
@Data
public class DeptUserRole {
    private Integer id;
    @NotNull(message = "部门id不能为空")
    private Integer deptId;
    @NotNull(message = "职位id不能为空")
    private Integer positionId;
    @NotBlank(message = "用户id不能为空")
    private String userId;
}

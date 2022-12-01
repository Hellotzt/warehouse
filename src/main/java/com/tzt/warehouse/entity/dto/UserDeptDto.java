package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author：帅气的汤
 * 用户部门职位dto
 */
@Data
public class UserDeptDto {
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;
    /**
     * 部门id
     */
    @NotBlank(message = "部门id不能为空")
    private String deptId;
    /**
     * 职位id
     */
    @NotBlank(message = "职位id不能为空")
    private String positionId;
}

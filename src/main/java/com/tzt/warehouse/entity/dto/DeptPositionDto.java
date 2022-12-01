package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author：帅气的汤
 */
@Data
public class DeptPositionDto {
    /**
     * 部门id
     */
    @NotBlank
    private String deptId;
    /**
     * 职位id列表（字符串用逗号分割）
     */
    private String positionIds;
}

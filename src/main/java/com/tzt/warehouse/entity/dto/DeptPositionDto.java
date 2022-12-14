package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author：帅气的汤
 */
@Data
public class DeptPositionDto {
    /**
     * 部门id
     */
    @NotBlank(message = "部门id不能为空")
    private String deptId;
    /**
     * 职位id列表
     */
    private List<String> positionIdList;
}

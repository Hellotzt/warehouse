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
    private String positionIds;
    /**
     * 职位id
     */
    // private String positionId;
    // /**
    //  * 职位名称
    //  */
    // private String positionName;
    // /**
    //  * 部门名称
    //  */
    // private String deptName;

}

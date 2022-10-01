package com.tzt.warehouse.entity.dto;

import lombok.Data;

/**
 * @author：帅气的汤
 * 部门dto
 */
@Data
public class DeptDto extends PageDto{
    /**
     * 上级id
     */
    private String pid;
}

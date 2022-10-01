package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tzt
 * 通用搜索实体
 */
@Data
public class SearchDTO {

    private String name;

    @NotNull(message = "页码不能为空")
    private Integer current=0;

    @NotNull(message = "页数不能为空")
    private Integer size=10;

    private Integer status;

    private String id;
}

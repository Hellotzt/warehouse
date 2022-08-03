package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author：帅气的汤
 */
@Data
public class PageDto {
    @NotNull(message = "页码不能为空")
    private Integer current=1;

    @NotNull(message = "页数不能为空")
    private Integer size=10;
}

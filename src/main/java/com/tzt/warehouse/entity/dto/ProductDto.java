package com.tzt.warehouse.entity.dto;

import com.tzt.warehouse.entity.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author：帅气的汤
 */
@Data
public class ProductDto extends Product {
    @NotNull(message = "页码不能为空")
    private Long current = 1L;

    @NotNull(message = "页数不能为空")
    private Long size = 10L;

    private Integer min;
    private Integer max;
}

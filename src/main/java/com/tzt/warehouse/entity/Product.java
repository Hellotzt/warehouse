package com.tzt.warehouse.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 产品表(Product)表实体类
 *
 * @author tzt
 * @since 2022-09-30 01:49:50
 */

@Data
@Accessors(chain = true)
public class Product extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 总材质
     */
    private String texture;
    /**
     * 产品颜色
     */
    private String color;
    /**
     * 产品尺寸
     */
    private String productSize;
    /**
     * 产品数量
     */
    private Integer quantity;
    /**
     * 产品型号
     */
    private String model;
    /**
     * 产品重量
     */
    private String weight;
    /**
     * 价格
     */
    private Double price;
    /**
     * 备注
     */
    private String remark;




}


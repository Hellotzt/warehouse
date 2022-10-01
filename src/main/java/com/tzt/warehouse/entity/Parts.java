package com.tzt.warehouse.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 配件表(Parts)表实体类
 *
 * @author tzt
 * @since 2022-09-30 01:01:15
 */

@Data
@Accessors(chain = true)
public class Parts extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 配件名称
     */
    private String name;
    /**
     * 配件数量
     */
    private Integer size;
    /**
     * 配件材质
     */
    private String texture;
    /**
     * 配件颜色
     */
    private String color;
    /**
     * 配件型号
     */
    private String model;
    /**
     * 配件重量
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


package com.tzt.warehouse.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * (ProductImg)表实体类
 *
 * @author tzt
 * @since 2022-10-18 23:44:07
 */

@Data
@Accessors(chain = true)
public class ProductImg extends Model<ProductImg> {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 产品图片url
     */
    private String productUrl;
    /**
     * 创建人的用户id
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;


}


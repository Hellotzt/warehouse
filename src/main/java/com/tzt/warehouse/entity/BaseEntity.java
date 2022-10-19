package com.tzt.warehouse.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author：帅气的汤
 */
@Data
public class BaseEntity {
    /**
     * 创建人的用户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)

    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic(value = "0",delval = "1")
    private Integer delFlag;
}

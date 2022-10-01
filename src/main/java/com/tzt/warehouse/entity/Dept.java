package com.tzt.warehouse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author：帅气的汤
 * 部门表
 */
@Data
public class Dept extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    /**
     * 上级部门id
     */
    private Integer pid;

    private int seq;

    @TableField(exist = false)
    private List<Dept> children;
}

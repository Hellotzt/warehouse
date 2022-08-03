package com.tzt.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author：帅气的汤
 * 部门表
 */
@Data
public class Dept {
    @TableId
    private int id;
    /**
     * 部门名称
     */
    private String deptName;
    private int seq;
}

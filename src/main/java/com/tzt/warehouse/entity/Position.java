package com.tzt.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author：帅气的汤
 * 职位表
 */
@Data
public class Position {
    @TableId
    private Integer id;
    /**
     * 职位名称
     */
    private String positionName;
    private int seq;
}

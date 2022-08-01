package com.tzt.warehouse.entity;

import lombok.Data;

/**
 * @author：帅气的汤
 * 职位表
 */
@Data
public class Position {
    private int id;
    /**
     * 职位名称
     */
    private String positionName;
    private int seq;
}

package com.tzt.warehouse.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * (Tag)表实体类
 *
 * @author tzt
 * @since 2022-10-01 21:37:30
 */

@Data
@Accessors(chain = true)
public class Tag extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private String id;
    private String tagName;
    private Integer tagNum;

}


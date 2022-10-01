package com.tzt.warehouse.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门职位表(DeptPosition)表实体类
 *
 * @author tzt
 * @since 2022-09-29 22:54:24
 */

@Data
@Accessors(chain = true)
public class DeptPosition extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private String id;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 职位名称
     */
    private String positionName;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 机构id
     */
    private String positionId;


}


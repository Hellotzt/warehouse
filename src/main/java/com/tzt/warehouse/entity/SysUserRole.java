package com.tzt.warehouse.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * (SysUserRole)表实体类
 *
 * @author makejava
 * @since 2022-09-18 23:41:45
 */
@SuppressWarnings("serial")
@Data
@Accessors(chain = true)
public class SysUserRole extends BaseEntity {
    /**
     * 用户id
     */
    @TableId
    private String userId;
    /**
     * 角色id
     */
    private String roleId;


}


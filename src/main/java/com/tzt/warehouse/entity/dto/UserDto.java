package com.tzt.warehouse.entity.dto;

import lombok.Data;

/**
 * @author：帅气的汤
 */
@Data
public class UserDto extends PageDto{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号状态（0正常 1停用 2审核中）
     */
    private String status;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 注册开始时间
     */
    private String beginTime;
    /**
     * 注册结束时间
     */
    private String endTime;

}

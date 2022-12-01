package com.tzt.warehouse.entity.vo;

import lombok.Data;

/**
 * @author：帅气的汤
 */
@Data
public class UserVo {
    /**
     * 主键
     */
    private String id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账号状态（0正常 1停用 2审核中）
     */
    private String status;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户性别（0女，1男，2未知）
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型（0超级管理员，1管理员，2普通用户）
     */
    private String userType;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 职位id
     */
    private String positionId;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 薪资
     */
    private String salary;

    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 职位名称
     */
    private String positionName;
}

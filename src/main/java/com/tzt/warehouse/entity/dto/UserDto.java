package com.tzt.warehouse.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

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
     * 用户类型（0超级管理员，1管理员，2普通用户）
     */
    private String userType;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 职位
     */
    private String position;
    /**
     * 部门
     */
    private String department;
    /**
     * 薪资
     */
    private BigDecimal salary;
    /**
     * 薪资最高值
     */
    private String max;
    /**
     * 薪资最小值
     */
    private String min;
    /**
     * 年龄
     */
    @TableField(exist = false)
    private String age;
}

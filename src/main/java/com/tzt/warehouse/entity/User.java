package com.tzt.warehouse.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户表(UserDto)实体类
 *
 * @author 帅气的汤
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    
    /**
    * 主键
    */
    @TableId
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
    * 密码
    */
    private String password;
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
    * 创建人的用户id
    */
    private String createBy;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新人
    */
    private String updateBy;
    /**
    * 更新时间
    */
    private Date updateTime;
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
    private String salary;
    /**
     * 年龄
     */
    @TableField(exist = false)
    private String age;
}
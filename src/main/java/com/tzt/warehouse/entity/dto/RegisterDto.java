package com.tzt.warehouse.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * UserDto
 *
 * @author 帅气的汤
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    /**
    * 用户名
    */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
    * 密码
    */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
    * 手机号
    */
    @NotBlank(message = "手机号不能为空")
    @Size(min = 11,max = 11,message = "联系电话长度有误")
    private String phone;
    /**
    * 用户性别（0女，1男，2未知）
    */
    @NotBlank(message = "性别不能为空")
    private String sex;

    /**
     * 身份证
     */
    @NotBlank(message = "身份证不能为空")
    @Size(min = 18,max = 18,message = "身份证长度有误")
    private String idCard;


}
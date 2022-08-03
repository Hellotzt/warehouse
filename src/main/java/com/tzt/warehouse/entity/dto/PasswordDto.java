package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author：帅气的汤
 */
@Data
public class PasswordDto {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}

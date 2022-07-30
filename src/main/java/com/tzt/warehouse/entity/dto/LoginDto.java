package com.tzt.warehouse.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author：帅气的汤
 */
@Data
public class LoginDto {
    @NotNull(message = "身份证不能为空")
    private Integer idCard;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "验证码不能为空")
    private String verify;
}
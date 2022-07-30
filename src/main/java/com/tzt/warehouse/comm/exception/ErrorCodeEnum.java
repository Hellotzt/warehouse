package com.tzt.warehouse.comm.exception;

/**
 * 错误码枚举
 */
public enum ErrorCodeEnum {
    /**
     * 系统错误
     */
    SYSTEM_ERROR(50000, "完了芭比q了"),

    ACCOUNT_NULL(406, "参数不能为空"),
    EXIST_ID(407, "ID已存在"),
    NULL_ID(408, "ID不存在"),
    VERIFY_ERROR(409, "验证码错误"),
    NULL_POINTER(410, "空指针"),
    SAVE_ERROR(411, "保存失败"),
    VR_UID_ERROR(411, "该账户已预约"),
    VR_ERROR(411, "该设备已被预约"),
    ACCOUNT_ERROR(412, "用户或密码有误"),
    RECORD_ERROR(412, "订单号或用户id有误"),
    EMAIL_EXIST(413, "该邮箱已经存在"),
    USER_EXIST(414, "该用户已经存在"),
    EMAIL_NULL(415, "该邮箱尚未注册");

    private final int code;

    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
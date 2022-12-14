package com.tzt.warehouse.comm.base;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import lombok.Data;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    private String time;

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static ResponseResult<Object> success() {
       return new ResponseResult<>(200,"操作成功",null);
    }

    public static ResponseResult<Object> success(Object data) {
        return new ResponseResult<>(200,"操作成功",data);
    }

    public ResponseResult(ErrorCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMessage();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
    public ResponseResult( T data) {
        this.code = 200;
        this.data = data;
    }
    public ResponseResult(Page page) {
        this.code = 200;
        HashMap<String, Object> map = new HashMap<>();
        map.put("result",page.getRecords());
        map.put("total", page.getTotal());
        map.put("current", page.getCurrent());
        map.put("size", page.getSize());
        this.data = (T) map;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = DateUtil.now();
    }
}
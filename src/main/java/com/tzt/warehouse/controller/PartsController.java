package com.tzt.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Parts;
import com.tzt.warehouse.service.PartsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 配件表(Parts)表控制层
 *
 * @author tzt
 * @since 2022-09-30 01:01:14
 */
@RestController
@RequestMapping("parts")
public class PartsController {
    /**
     * 服务对象
     */
    @Resource
    private PartsService partsService;

    @PostMapping("/add")
    public ResponseResult<Object> addParts(@RequestBody Parts parts){
        return new ResponseResult<>(partsService.saveParts(parts));
    }

    @PostMapping("/list")
    public ResponseResult<Object> partsList(@RequestBody Parts parts){
        return new ResponseResult<>(partsService.list(new LambdaQueryWrapper<Parts>().eq(Parts::getProductId,parts.getProductId())));
    }

}


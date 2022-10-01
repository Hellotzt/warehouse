package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.entity.Parts;

/**
 * 配件表(Parts)表服务接口
 *
 * @author tzt
 * @since 2022-09-30 01:01:15
 */
public interface PartsService extends IService<Parts> {

    Object saveParts(Parts parts);
}


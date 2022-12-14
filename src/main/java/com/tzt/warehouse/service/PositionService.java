package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Position;

/**
 * 职位
 * @author 帅气的汤
 */
public interface PositionService extends IService<Position> {
    ResponseResult<Object> positionList();

    ResponseResult<Object> deletePosition(Position position);
}

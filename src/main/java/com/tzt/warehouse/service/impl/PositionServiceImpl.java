package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.DeptUserRole;
import com.tzt.warehouse.entity.Position;
import com.tzt.warehouse.mapper.PositionDao;
import com.tzt.warehouse.service.DeptUserRoleService;
import com.tzt.warehouse.service.PositionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author：帅气的汤
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionDao, Position> implements PositionService {
    @Resource
    private DeptUserRoleService deptUserRoleService;
    @Override
    public ResponseResult<Object> positionList() {
        QueryWrapper<Position> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("seq");
        return new ResponseResult<>(this.list(wrapper));
    }

    @Override
    public ResponseResult<Object> deletePosition(Position position) {
        if (position.getId() == null) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        // 如果该职位有人，不允许删除
        DeptUserRole deptUserRole = deptUserRoleService.getOne(new QueryWrapper<DeptUserRole>().eq("position_id", position.getId()));
        if (deptUserRole!=null){
            return new ResponseResult<>(ErrorCodeEnum.POSITION_ERROR);
        }
        boolean b = this.removeById(position.getId());
        if (!b) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        return ResponseResult.success();
    }
}

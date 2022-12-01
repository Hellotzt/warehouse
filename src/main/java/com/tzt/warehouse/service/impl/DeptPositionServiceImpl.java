package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.DeptPosition;
import com.tzt.warehouse.entity.Position;
import com.tzt.warehouse.entity.dto.DeptPositionDto;
import com.tzt.warehouse.mapper.DeptPositionMapper;
import com.tzt.warehouse.service.DeptPositionService;
import com.tzt.warehouse.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门职位表(DeptPosition)表服务实现类
 *
 * @author tzt
 * @since 2022-09-25 02:36:07
 */
@Service("deptPositionService")
@Slf4j
public class DeptPositionServiceImpl extends ServiceImpl<DeptPositionMapper, DeptPosition> implements DeptPositionService {
    private final PositionService positionService;

    public DeptPositionServiceImpl(PositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public ResponseResult<Object> addDeptPosition(DeptPositionDto deptPositionDto) {
        List<String> addIdList = Arrays.asList(deptPositionDto.getPositionIds().split(","));
        log.info("给部门：{}添加的职位id：{}",deptPositionDto.getDeptId(),deptPositionDto.getPositionIds());
        List<String> idList = listObjs(new LambdaQueryWrapper<DeptPosition>()
                .eq(DeptPosition::getDeptId, deptPositionDto.getDeptId())
                .select(DeptPosition::getId), Object::toString);
        removeByIds(idList);
        ArrayList<DeptPosition> deptPositionList = new ArrayList<>();
        addIdList.forEach(id -> {
            DeptPosition deptPosition = new DeptPosition();
            deptPosition.setDeptId(deptPositionDto.getDeptId()).setPositionId(id);
            deptPositionList.add(deptPosition);
        });
        log.info("保存的部门职位关系：{}", JSON.toJSON(deptPositionList));
        this.saveBatch(deptPositionList);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> getDeptPosition(DeptPositionDto deptPositionDto) {
        DeptPosition deptPosition = this.getOne(new LambdaQueryWrapper<DeptPosition>().eq(DeptPosition::getDeptId, deptPositionDto.getDeptId()));

        List<String> positionIdList = Arrays.stream(deptPosition.getPositionId().split(",")).collect(Collectors.toList());
        List<Position> positionList = null;
        if (ObjectUtil.isNotEmpty(positionIdList)){
             positionList = positionService.list(new LambdaQueryWrapper<Position>().in(Position::getId, positionIdList));
        }

        return new ResponseResult<>(positionList);
    }
}


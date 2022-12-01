package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.BusinessException;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.DeptPosition;
import com.tzt.warehouse.entity.dto.DeptPositionDto;
import com.tzt.warehouse.entity.vo.DeptPositionVo;
import com.tzt.warehouse.mapper.DeptPositionMapper;
import com.tzt.warehouse.service.DeptPositionService;
import com.tzt.warehouse.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门职位表(DeptPosition)表服务实现类
 *
 * @author tzt
 * @since 2022-09-25 02:36:07
 */
@Service("deptPositionService")
@Slf4j
public class DeptPositionServiceImpl extends ServiceImpl<DeptPositionMapper, DeptPosition> implements DeptPositionService {
    private final DeptService deptService;

    public DeptPositionServiceImpl(DeptService deptService) {
        this.deptService = deptService;
    }

    @Override
    public ResponseResult<Object> addDeptPosition(DeptPositionDto deptPositionDto) {
        Dept dept = deptService.getOne(new LambdaQueryWrapper<Dept>()
                .eq(Dept::getId, deptPositionDto.getDeptId()));
        if (ObjectUtil.isNull(dept)){
            throw new BusinessException(ErrorCodeEnum.DEPT_NULL);
        }
        List<String> idList = listObjs(new LambdaQueryWrapper<DeptPosition>()
                .eq(DeptPosition::getDeptId, deptPositionDto.getDeptId())
                .select(DeptPosition::getId), Object::toString);
        log.info("机构：{}下绑定的职位信息：{}",deptPositionDto.getDeptId(), JSON.toJSON(idList));
        this.removeByIds(idList);
        ArrayList<DeptPosition> deptPositionList = new ArrayList<>();
        deptPositionDto.getPositionIdList().forEach(id -> {
            DeptPosition deptPosition = new DeptPosition();
            deptPosition.setDeptId(deptPositionDto.getDeptId())
                    .setPositionId(id);
            deptPositionList.add(deptPosition);
        });
        log.info("保存的部门职位关系：{}", JSON.toJSON(deptPositionList));
        this.saveBatch(deptPositionList);
        return ResponseResult.success();
    }

    /**
     * 获取部门下的职位
     */
    @Override
    public ResponseResult<Object> getDeptPosition(DeptPositionDto deptPositionDto) {
        List<DeptPositionVo> deptPositionList = baseMapper.getDeptPosition(deptPositionDto.getDeptId());
        return ResponseResult.success(deptPositionList);
    }
}


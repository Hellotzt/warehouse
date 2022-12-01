package com.tzt.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzt.warehouse.entity.DeptPosition;
import com.tzt.warehouse.entity.vo.DeptPositionVo;

import java.util.List;

/**
 * 部门职位表(DeptPosition)表数据库访问层
 *
 * @author tzt
 * @since 2022-09-25 02:36:06
 */
public interface DeptPositionMapper extends BaseMapper<DeptPosition> {
    /**
     * 获取部门下的职位信息
     */
    List<DeptPositionVo> getDeptPosition(String deptId);
}


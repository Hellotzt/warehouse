package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.Dept;
import com.tzt.warehouse.entity.DeptUserRole;
import com.tzt.warehouse.entity.dto.DeptDto;
import com.tzt.warehouse.mapper.DeptDao;
import com.tzt.warehouse.service.DeptService;
import com.tzt.warehouse.service.DeptUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：帅气的汤
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {
    @Resource
    private DeptUserRoleService deptUserRoleService;

    @Override
    public ResponseResult<Object> deptList(DeptDto deptDto) {
        LambdaQueryWrapper<Dept> wrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isBlank(deptDto.getPid())) {
            wrapper.isNull(Dept::getPid);
        } else {
            wrapper.eq(Dept::getPid, deptDto.getPid());
        }
        wrapper.orderByAsc(Dept::getSeq);
        List<Dept> list = this.list(wrapper);
        return new ResponseResult<>(list);
    }

    @Override
    public ResponseResult<Object> deleteDept(Dept dept) {
        if (dept.getId() == null) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        // 如果该部门有人，不允许删除
        DeptUserRole deptUserRole = deptUserRoleService.getOne(new QueryWrapper<DeptUserRole>().eq("dept_id", dept.getId()));
        if (deptUserRole != null) {
            return new ResponseResult<>(ErrorCodeEnum.DEPT_ERROR);
        }
        boolean b = this.removeById(dept.getId());
        if (!b) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> all() {
        List<Dept> depts = this.list();
        ArrayList<Dept> deptList = new ArrayList<>();
        List<Dept> oneList = null;
        if (ObjectUtil.isNotEmpty(depts)) {
            oneList = depts.stream().filter(dept -> dept.getPid() == null).collect(Collectors.toList());
            forEachDept(oneList, depts);
        }

        return new ResponseResult<>(oneList);
    }

    private List<Dept> forEachDept(List<Dept> oneList,List<Dept> depts){
        oneList.forEach(dept -> {
            List<Dept> deptChild = depts.stream().filter(menu -> dept.getId().equals(menu.getPid())).collect(Collectors.toList());
            dept.setChildren(deptChild);
            if (ObjectUtil.isEmpty(deptChild)){
                return;
            }
            forEachDept(deptChild, depts);
        });
        return oneList;
    }
}

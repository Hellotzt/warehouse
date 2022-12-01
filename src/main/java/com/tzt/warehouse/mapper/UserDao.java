package com.tzt.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.UserDto;
import com.tzt.warehouse.entity.vo.UserVo;
import org.springframework.stereotype.Repository;

/**
 * @author：帅气的汤
 */
@Repository
public interface UserDao extends BaseMapper<User> {
    Page<UserVo> getUserList(Page<UserVo> page, UserDto userDto);
}

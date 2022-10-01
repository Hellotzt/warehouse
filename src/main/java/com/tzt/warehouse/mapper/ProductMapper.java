package com.tzt.warehouse.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.tzt.warehouse.entity.Product;

/**
 * 产品表(Product)表数据库访问层
 *
 * @author tzt
 * @since 2022-09-30 01:00:59
 */
public interface ProductMapper extends BaseMapper<Product> {

}


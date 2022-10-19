package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.mapper.ProductImgMapper;
import com.tzt.warehouse.entity.ProductImg;
import com.tzt.warehouse.service.ProductImgService;
import org.springframework.stereotype.Service;

/**
 * (ProductImg)表服务实现类
 *
 * @author tzt
 * @since 2022-10-18 23:44:08
 */
@Service("productImgService")
public class ProductImgServiceImpl extends ServiceImpl<ProductImgMapper, ProductImg> implements ProductImgService {

}


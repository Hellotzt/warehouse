package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.entity.Product;

/**
 * 产品表(Product)表服务接口
 *
 * @author tzt
 * @since 2022-09-30 01:00:59
 */
public interface ProductService extends IService<Product> {

    Object saveProduct(Product product);

    void deleteProduct(Product product);
}


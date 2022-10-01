package com.tzt.warehouse.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Product;
import com.tzt.warehouse.entity.dto.ProductDto;
import com.tzt.warehouse.service.PartsService;
import com.tzt.warehouse.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 产品表(Product)表控制层
 *
 * @author tzt
 * @since 2022-09-30 01:00:59
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private final PartsService partsService;
    /**
     * 服务对象
     */
    @Resource
    private ProductService productService;

    public ProductController(PartsService partsService) {
        this.partsService = partsService;
    }

    @PostMapping("/add")
    public ResponseResult<Object> addProduct(@RequestBody Product product) {

        return new ResponseResult<>(productService.saveProduct(product));
    }

    @PostMapping("/update")
    public ResponseResult<Object> updateProduct(@RequestBody Product product) {
        return new ResponseResult<>(productService.updateById(product));
    }

    @PostMapping("/list")
    public ResponseResult<Object> productList(@RequestBody ProductDto product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(product.getName()), Product::getName, product.getName());
        wrapper.like(StrUtil.isNotBlank(product.getColor()), Product::getColor, product.getColor());
        wrapper.like(StrUtil.isNotBlank(product.getModel()), Product::getModel, product.getModel());
        wrapper.between(product.getMin() != null && product.getMax() != null, Product::getPrice, product.getMin(), product.getMax());
        Page<Product> page = productService.page(new Page<>(product.getCurrent(), product.getSize()), wrapper);

        return new ResponseResult<>(page);
    }

    @PostMapping("/delete")
    public ResponseResult<Object> deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product);


        return ResponseResult.success();
    }
}


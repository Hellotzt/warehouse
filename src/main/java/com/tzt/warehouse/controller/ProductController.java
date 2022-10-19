package com.tzt.warehouse.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Product;
import com.tzt.warehouse.entity.ProductImg;
import com.tzt.warehouse.entity.dto.ProductDto;
import com.tzt.warehouse.service.PartsService;
import com.tzt.warehouse.service.ProductImgService;
import com.tzt.warehouse.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private final ProductService productService;
    private final ProductImgService productImgService;

    public ProductController(PartsService partsService, ProductService productService, ProductImgService productImgService) {
        this.partsService = partsService;
        this.productService = productService;
        this.productImgService = productImgService;
    }

    @PostMapping("/add")
    public ResponseResult<Object> addProduct( Product product,List<MultipartFile> fileList) {
        return productService.saveProduct(product,fileList);
    }

    @PostMapping("/deleteProductImgList")
    public ResponseResult<Object> deleteProductImgList(@RequestBody List<String> imgIdList) {
        return productService.deleteProductImgList(imgIdList);
    }

    @PostMapping("/saveProductImgList")
    public ResponseResult<Object> saveProductImgList(List<MultipartFile> productImgList, String productId) {
        return productService.saveProductImgList(productImgList,productId);
    }

    @PostMapping("/update")
    public ResponseResult<Object> updateProduct(@RequestBody Product product) {
        return new ResponseResult<>(productService.updateById(product));
    }

    @PostMapping("/list")
    public ResponseResult<Object> productList(@RequestBody ProductDto productDto) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(productDto.getName()), Product::getName, productDto.getName());
        wrapper.like(StrUtil.isNotBlank(productDto.getColor()), Product::getColor, productDto.getColor());
        wrapper.like(StrUtil.isNotBlank(productDto.getModel()), Product::getModel, productDto.getModel());
        wrapper.between(productDto.getMin() != null && productDto.getMax() != null, Product::getPrice, productDto.getMin(), productDto.getMax());
        Page<Product> page = productService.page(new Page<>(productDto.getCurrent(), productDto.getSize()), wrapper);
        page.getRecords().forEach(vo ->{
            vo.setProductImgList(productImgService.list(new LambdaQueryWrapper<ProductImg>()
                    .eq(ProductImg::getProductId, vo.getId())
                    .select(ProductImg::getId,ProductImg::getProductUrl)));
        } );
        return new ResponseResult<>(page);
    }

    @PostMapping("/delete")
    public ResponseResult<Object> deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product);
        return ResponseResult.success();
    }
}


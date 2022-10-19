package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 产品表(Product)表服务接口
 *
 * @author tzt
 * @since 2022-09-30 01:00:59
 */
public interface ProductService extends IService<Product> {
    /**
     * 保存产品
     * @param product 产品
     * @return
     */
    ResponseResult<Object> saveProduct(Product product,List<MultipartFile> fileList);

    /**
     * 删除产品
     * @param product
     */
    void deleteProduct(Product product);

    /**
     * 批量保存产品图片
     * @param product
     * @return
     */
    ResponseResult<Object> saveProductImgList(List<MultipartFile> productImgList, String productId);

    /**
     * 批量删除产品图片
     * @param imgIdList
     * @return
     */
    ResponseResult<Object> deleteProductImgList(List<String> imgIdList);
}


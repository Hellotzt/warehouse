package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.BusinessException;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.MinioUtils;
import com.tzt.warehouse.entity.Parts;
import com.tzt.warehouse.entity.Product;
import com.tzt.warehouse.entity.ProductImg;
import com.tzt.warehouse.mapper.ProductMapper;
import com.tzt.warehouse.service.PartsService;
import com.tzt.warehouse.service.ProductImgService;
import com.tzt.warehouse.service.ProductService;
import com.tzt.warehouse.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 产品表(Product)表服务实现类
 *
 * @author tzt
 * @since 2022-09-30 01:00:59
 */
@Service("productService")
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    private final TagService tagService;
    private final PartsService partsService;
    private final MinioUtils minioUtils;
    private final ProductImgService productImgService;
    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.endpoint}")
    private String endpoint;


    public ProductServiceImpl(TagService tagService, PartsService partsService, MinioUtils minioUtils, ProductImgService productImgService) {
        this.tagService = tagService;
        this.partsService = partsService;
        this.minioUtils = minioUtils;
        this.productImgService = productImgService;
    }

    @Override
    public ResponseResult<Object> saveProduct(Product product,List<MultipartFile> fileList) {
        List<String> tagList = Arrays.asList(product.getTexture().split(","));
        tagService.forEachSave(tagList);
        this.save(product);
        // ArrayList<ProductImg> productImgList = new ArrayList<>();
        // List<MultipartFile> fileList = product.getFileList();
        this.saveProductImgList(fileList, product.getId());
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void deleteProduct(Product product) {
        Product product1 = this.getById(product.getId());
        if (ObjectUtil.isNotNull(product1)) {
            this.removeById(product1.getId());
            HashSet<String> tagSet = new HashSet<>(Arrays.asList(product1.getTexture().split(",")));

            List<Parts> partsList = partsService.list(new LambdaQueryWrapper<Parts>().eq(Parts::getProductId, product1.getId()));
            if (ObjectUtil.isNotEmpty(partsList)){
                List<Integer> stringList = new ArrayList<>();
                partsList.forEach(parts -> {
                    stringList.add(parts.getId());
                    HashSet<String> partsTagSet = new HashSet<>(Arrays.asList(parts.getTexture().split(",")));
                    tagSet.addAll(partsTagSet);
                });
                partsService.removeByIds(stringList);
            }
            tagService.forEachDelete(tagSet);

        }
        productImgService.remove(new LambdaQueryWrapper<ProductImg>()
                .eq(ProductImg::getProductId, product1.getId()));

    }

    @Override
    public ResponseResult<Object> saveProductImgList(List<MultipartFile> productImgList, String productId) {
        log.info("开始保存产品图片>>>");
        List<ProductImg> imgList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(productImgList)){
            productImgList.forEach(file -> {
                String originalFilename = file.getOriginalFilename();
                String fileName = IdUtil.simpleUUID() + originalFilename;
                try {
                    minioUtils.putObject(bucketName, file, fileName, file.getContentType());
                } catch (Exception e) {
                    log.error("上传到minio失败：{}",e.getMessage());
                    throw new BusinessException(ErrorCodeEnum.SAVE_ERROR);
                }
                String productUrl = endpoint + "/" + bucketName + "/" + fileName;
                ProductImg productImg = new ProductImg();
                productImg.setProductId(productId).setProductUrl(productUrl);
                imgList.add(productImg);
                log.info("上传图片成功，图片地址：{}",productUrl);
            });
            productImgService.saveBatch(imgList);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> deleteProductImgList(List<String> imgIdList) {
        productImgService.removeByIds(imgIdList);
        return ResponseResult.success();
    }
}


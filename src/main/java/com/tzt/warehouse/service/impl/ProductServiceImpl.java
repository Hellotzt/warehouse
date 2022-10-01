package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.entity.Parts;
import com.tzt.warehouse.entity.Product;
import com.tzt.warehouse.mapper.ProductMapper;
import com.tzt.warehouse.service.PartsService;
import com.tzt.warehouse.service.ProductService;
import com.tzt.warehouse.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    private final TagService tagService;
    private final PartsService partsService;


    public ProductServiceImpl(TagService tagService, PartsService partsService) {
        this.tagService = tagService;
        this.partsService = partsService;
    }

    @Override
    public Object saveProduct(Product product) {
        List<String> tagList = Arrays.asList(product.getTexture().split(","));
        tagService.forEachSave(tagList);
        return this.save(product);
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

    }
}


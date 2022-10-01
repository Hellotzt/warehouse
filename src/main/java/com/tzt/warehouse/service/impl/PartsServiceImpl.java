package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.mapper.PartsMapper;
import com.tzt.warehouse.entity.Parts;
import com.tzt.warehouse.service.PartsService;
import com.tzt.warehouse.service.TagService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 配件表(Parts)表服务实现类
 *
 * @author tzt
 * @since 2022-09-30 01:01:15
 */
@Service("partsService")
public class PartsServiceImpl extends ServiceImpl<PartsMapper, Parts> implements PartsService {
    private final TagService tagService;

    public PartsServiceImpl(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public Object saveParts(Parts parts) {
        List<String> tagList = Arrays.asList(parts.getTexture().split(","));
        tagService.forEachSave(tagList);
        return this.save(parts);
    }
}


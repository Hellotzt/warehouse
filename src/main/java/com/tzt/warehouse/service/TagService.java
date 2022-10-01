package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.entity.Tag;

import java.util.HashSet;
import java.util.List;

/**
 * (Tag)表服务接口
 *
 * @author tzt
 * @since 2022-10-01 21:37:30
 */
public interface TagService extends IService<Tag> {
    /**
     * 循环插入标签表
     * @param tagList
     */
    void forEachSave(List<String> tagList);
    void forEachDelete(HashSet<String> tagList);
}


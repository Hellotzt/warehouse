package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.entity.Tag;
import com.tzt.warehouse.mapper.TagMapper;
import com.tzt.warehouse.service.TagService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author tzt
 * @since 2022-10-01 21:37:31
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public void forEachSave(List<String> tagList) {
        tagList.forEach(tag->{
            Tag one = this.getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagName, tag));
            if(ObjectUtil.isNull(one)){
                Tag newTag = new Tag();
                newTag.setTagName(tag).setTagNum(1);
                this.save(newTag);
            }else {
                one.setTagNum(one.getTagNum()+1);
                this.updateById(one);
            }
        });
    }

    @Override
    public void forEachDelete(HashSet<String> tagList) {
        tagList.forEach(tag->{
            Tag one = this.getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagName, tag));
            if(ObjectUtil.isNotNull(one)) {
                one.setTagNum(one.getTagNum()-1);
                this.updateById(one);
            }
        });
    }
}


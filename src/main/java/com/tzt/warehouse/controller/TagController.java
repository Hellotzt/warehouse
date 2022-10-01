package com.tzt.warehouse.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.Tag;
import com.tzt.warehouse.service.TagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Tag)表控制层
 *
 * @author tzt
 * @since 2022-10-01 21:37:29
 */
@RestController
@RequestMapping("tag")
public class TagController {
    /**
     * 服务对象
     */
    @Resource
    private TagService tagService;

    @PostMapping("/getTag")
    public ResponseResult<Object> getTag(){
        List<Tag> list = tagService.list(new LambdaQueryWrapper<Tag>().ne(Tag::getTagNum, 0));
        return new ResponseResult<>(list);
    }

}


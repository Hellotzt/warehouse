package com.tzt.warehouse.controller;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.Position;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.service.PositionService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author：帅气的汤
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Resource
    private PositionService positionService;

    @PostMapping("/list")
    public ResponseResult<Object> positionList(@RequestBody @Valid SearchDTO searchDTO) {
        return positionService.positionList(searchDTO);
    }

    @PostMapping("/add")
    public ResponseResult<Object> add(@RequestBody Position position) {
        if (!StringUtils.hasText(position.getPositionName())) {
            return new ResponseResult<>(ErrorCodeEnum.ACCOUNT_NULL);
        }
        // PositionName重复校验
        try {
            positionService.save(position);
        } catch (Exception e) {
            return new ResponseResult<>(ErrorCodeEnum.PARAM_EXIST);
        }
        return ResponseResult.success();
    }

    @PostMapping("/delete")
    public ResponseResult<Object> delete(@RequestBody Position position) {
       return positionService.deletePosition(position);

    }

    @PostMapping("/update")
    public ResponseResult<Object> update(@RequestBody @Valid Position position) {
        boolean b = positionService.updateById(position);
        if (!b) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        return ResponseResult.success();
    }
}

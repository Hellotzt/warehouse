package com.tzt.warehouse.comm.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import com.tzt.warehouse.entity.BaseEntity;
import com.tzt.warehouse.entity.LoginUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) metaObject.getOriginalObject();

            Date current = new Date();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseEntity.getCreateTime())) {
                baseEntity.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseEntity.getUpdateTime())) {
                baseEntity.setUpdateTime(current);
            }
            
            String userId = null;
            LoginUser loginUser = UserUtlis.get();
            if (loginUser!=null){
                userId=loginUser.getUser().getId();
            }
            
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getCreateBy())) {
                baseEntity.setCreateBy(userId);
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseEntity.getUpdateBy())) {
                baseEntity.setUpdateBy(userId.toString());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", new Date(), metaObject);
        }

        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object modifier = getFieldValByName("updateBy", metaObject);
        
        String userId = UserUtlis.get().getUser().getId();
        
        if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
            setFieldValByName("updateBy", userId, metaObject);
        }
    }
}

package com.xjaxl.user.common.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.xjaxl.user.common.base.AbstractBaseDTO;
import com.xjaxl.user.common.base.AbstractGeneralDTO;
import com.xjaxl.user.common.consts.CodeMiscHeadType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class EntityUtils {

    /** @param entity 对象 */
    public static void setBaseEntity(AbstractBaseDTO entity, CodeMiscHeadType idType) {
        boolean forUpdate = false;
        if (StrUtil.isNotEmpty(entity.getId())) {
            forUpdate = true;
        }
        if (entity instanceof AbstractGeneralDTO) {
            if (forUpdate) {
                ((AbstractGeneralDTO) entity).setUpdateTime(DateUtil.now());
                ((AbstractGeneralDTO)entity).setUpdaterId(ShiroUtils.getUserId());
            }
        }
        if (!forUpdate) {
            setIdEntity(entity,idType);
            entity.setCreateTime(DateUtil.now());
            entity.setCreatorId(ShiroUtils.getUserId());
        }
    }

    /** @param entity 对象 */
    public static void setIdEntity(AbstractBaseDTO entity, CodeMiscHeadType idType) {
        entity.setId(SysUtilMisc.genDBId(idType.getType()));
    }

    //java对象转map
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }

}

package com.xjaxl.user.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xjaxl.user.common.exceptions.BizException;

public class SystemUtils {

    /**
     *  校验是否是管理员
     */
    public static void checkPermissions() {
        String userType = ShiroUtils.getUserType();
        if (!StringUtils.equals(userType,ManaConstant.StringNumber.ZERO)) {
            throw new BizException("当前用户无权限");
        }
    }

}

package com.xjaxl.user.common.consts;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GlobalVariables {

    public static final List<String> STATUS_ZERO_ONE = CollUtil.newArrayList("0","1");
    public static final List<String> STATUS_ONE_TWO_THREE = CollUtil.newArrayList("1","2","3");


}

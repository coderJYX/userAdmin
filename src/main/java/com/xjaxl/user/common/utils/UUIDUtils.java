package com.xjaxl.user.common.utils;

import java.util.UUID;


public class UUIDUtils {
    public static String randomUUIDWithoutHyphen() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

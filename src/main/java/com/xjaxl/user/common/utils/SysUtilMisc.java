package com.xjaxl.user.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SysUtilMisc {

    /**
     * 产生DB存储的主键ID
     * @param head 表主键的前缀
     * @return 主键。构成：前缀+一串唯一的数字(18-20位)。
     */
    public static String genDBId(String head) {
        ElectricGlobalEnvConfiguration electricGlobalEnvConfiguration = SpringContextUtils.getBean(ElectricGlobalEnvConfiguration.class);

        if(head != null) {
            head = head.toUpperCase();
        }

        long id = GlobalIDGenerator.getInstance().nextId();
        return head != null ? head + electricGlobalEnvConfiguration.getPeffix()+ id : String.valueOf(id);
    }

}

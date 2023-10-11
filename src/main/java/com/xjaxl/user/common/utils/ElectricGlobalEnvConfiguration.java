package com.xjaxl.user.common.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
@Data
public class ElectricGlobalEnvConfiguration {

    @Value("${electric.global.id}")
    private Integer id;

    @Value("${electric.global.peffix}")
    private String peffix;

    @Value("${electric.global.cache.load-cache-data-enabled}")
    private boolean loadCacheDataEnabled;

    @Value("${electric.global.cache.load-page-limit-default}")
    private Integer loadPageLimitDefault;

}

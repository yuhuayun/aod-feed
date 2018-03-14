package com.sinosoft.aod.feed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author wangjunhua
 * 公共 Bean 声明配置
 * Created by LONGLEI on 2018/1/23.
 */
@Configuration
public class BeanConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}

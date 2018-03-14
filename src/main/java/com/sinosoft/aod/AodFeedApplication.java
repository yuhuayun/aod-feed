package com.sinosoft.aod;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 *
 * 高级名单管理启动类
 * @author wangjunhua
 *
 */
@Slf4j
@ComponentScan("com.sinosoft")
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
public class AodFeedApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AodFeedApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AodFeedApplication.class);
        app.run(args);

        log.info("Restful API 文档访问地址 http://localhost:8080/swagger-ui.html is success!");
    }

}

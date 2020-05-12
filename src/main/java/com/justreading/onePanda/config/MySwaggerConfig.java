package com.justreading.onePanda.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LYJ
 * @Description 配置Swagger2
 * @date 2020 年 02 月 15 日 11:17
 */

@Configuration
@EnableSwagger2
public class MySwaggerConfig {
    /**
     * 配置摘要
     * @return Docket
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.justreading.onePanda"))
                .build();
    }

    /**
     * 配置api的说明信息
     * @return ApiInfo
     */
    private  ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .description("这是OnePanda的后台api接口")
                .contact(new Contact("林永健",null,"1499755237@qq.com"))
                .title("OnePanda")
                .version("1.0.0")
                .build();
    }

}

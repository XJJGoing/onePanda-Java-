package com.justreading.onePanda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description 配置restTemplate并且添加解析规则
 * @date 2020 年 02 月 15 日 11:27
 */
@Configuration
public class MyRestTemplateConfig {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    /**
     * 添加相应的解析html 以及 plain的规则
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        List<HttpMessageConverter<?>> messageConverters = restTemplateBuilder.build().getMessageConverters();
        for(HttpMessageConverter<?> messageConverter : messageConverters){
           if(messageConverter instanceof MappingJackson2CborHttpMessageConverter){
               ArrayList<MediaType> mediaTypeArrayList = new ArrayList<>(messageConverter.getSupportedMediaTypes());
               mediaTypeArrayList.add(MediaType.TEXT_HTML);
               mediaTypeArrayList.add(MediaType.TEXT_PLAIN);
               ((MappingJackson2CborHttpMessageConverter) messageConverter).setSupportedMediaTypes(mediaTypeArrayList);
           }
        }
        return restTemplateBuilder
                .rootUri("127.0.0:8080")
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
    }
}

package com.justreading.onePanda.common;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author LYJ
 * @Description  腾讯云存储的配置
 * @date 2020 年 02 月 25 日 17:27
 */
@Component
@ConfigurationProperties(prefix = "spring.tengxun")
public class TenXunUtil {
     private String secretId;
     private String secretKey;

     //onePanda的存储桶
     private String onePandaBucket;
     private String area;
     private String path;

     @Bean
     public COSClient cosClient(){
         COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
         Region region = new Region(area);
         ClientConfig clientConfig = new ClientConfig(region);
         COSClient cosClient = new COSClient(cred,clientConfig);
         return cosClient;
     }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getOnePandaBucket() {
        return onePandaBucket;
    }

    public void setOnePandaBucket(String onePandaBucket) {
        this.onePandaBucket = onePandaBucket;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

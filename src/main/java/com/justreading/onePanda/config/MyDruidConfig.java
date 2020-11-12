package com.justreading.onePanda.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LYJ
 * @Description druid的配置
 * @date 2020 年 02 月 15 日 10:48
 */
@Configuration
public class MyDruidConfig {
    /**
     * @Description 返回数据源
     * @return DataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }
    

    /**
     * 配置访问druid数据源的servlet
     * @return servletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean servletBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,Object> map = new HashMap<>();
        map.put("loginUsername","LYJ");
        map.put("loginPassword","18178595973qwe");
        map.put("allow","");
        map.put("deny","");
        servletBean.setInitParameters(map);
        return servletBean;
    }
}

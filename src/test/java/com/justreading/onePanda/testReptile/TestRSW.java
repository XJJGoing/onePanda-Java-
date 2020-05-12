package com.justreading.onePanda.testReptile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 03 月 16 日 17:25
 */
@SpringBootTest
public class TestRSW {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试模拟登陆人事网
     */
    @Test
    public void testLogin(){

    }
}

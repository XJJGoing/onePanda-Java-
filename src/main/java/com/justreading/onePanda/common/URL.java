package com.justreading.onePanda.common;

/**
 * @author LYJ
 * @Description 存放url请求的枚举类
 * @date 2020 年 02 月 15 日 13:50
 */
public enum  URL {
    STUDENT_LOGIN("http://jwgl.just.edu.cn:8080/jsxsd/xk/LoginToXk"),
    STUDENT_GRADE("http://jwgl.just.edu.cn:8080/jsxsd/kscj/cjcx_list"),
    STUDENT_COURSE("http://jwgl.just.edu.cn:8080/jsxsd/xskb/xskb_list.do"),
    STUDENT_INFO("http://jwgl.just.edu.cn:8080/jsxsd/grxx/xsxx?Ves632DSdyV=NEW_XSD_XJCJ"),
    STUDENT_LOGOUT("http://jwgl.just.edu.cn:8080/jsxsd/xk/LoginToXk?method=exit&tktime=1583559882000"),
    STUDENT_PE("http://tyxy.just.edu.cn/index1.asp"),
    STUDENT_LOGIN_YZS("https://cas.v.just.edu.cn:4443/cas/login");
    private String url;

    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

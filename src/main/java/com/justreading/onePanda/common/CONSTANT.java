package com.justreading.onePanda.common;

/**
 * @author LYJ
 * @Description 存放一些常量类似于类似于学期，供自动爬虫使用
 * @date 2020 年 02 月 16 日 17:27
 */
public enum  CONSTANT {
    DEFAULT_TERM("2020-2021-1");
    private String defaultTerm;

    CONSTANT(String defaultTerm) {
        this.defaultTerm = defaultTerm;
    }

    public  String getDefaultTerm() {
        return defaultTerm;
    }

    public static String getDefaultTermForLogin(){
       String res = "";
       for(CONSTANT element : values()){
           res = element.getDefaultTerm();
       }
       return res;
    }
}

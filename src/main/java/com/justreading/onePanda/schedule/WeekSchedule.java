package com.justreading.onePanda.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author LYJ
 * @Description 自动进行周数的变化
 * @date 2020 年 02 月 17 日 13:53
 */
@Component
public class WeekSchedule {
    public static Integer NOW_WEEK = 2;

    public static Integer getNowWeek() {
        return NOW_WEEK;
    }

    public static void setNowWeek(Integer nowWeek) {
        NOW_WEEK = nowWeek;
    }

    @Scheduled(cron = "0 50 23 * * 7")
    public void changeWeek(){
        if(NOW_WEEK < 20 ){
            NOW_WEEK += 1;
        }
    }
}

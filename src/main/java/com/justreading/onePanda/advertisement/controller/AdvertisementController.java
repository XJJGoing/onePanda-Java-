package com.justreading.onePanda.advertisement.controller;

import com.justreading.onePanda.advertisement.entity.Advertisement;
import com.justreading.onePanda.advertisement.service.AdvertisementService;
import com.justreading.onePanda.common.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LYJ
 * @Description 娱乐的接口，这个实体并没有表去对应
 * @date 2020 年 02 月 28 日 21:50
 */
@RestController
@Api(tags = "娱乐模块，该模块并没有表")
@RequestMapping("/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @ApiOperation("获取写真所有图片的url")
    @GetMapping("/img")
    public ApiResponse<List<Advertisement>> getImagesUrl(){
        ApiResponse<List<Advertisement>> apiResponse = advertisementService.getImagesUrl();
        return apiResponse;
    }

    @GetMapping("/video")
    @ApiOperation("获取所有视频的url")
    public ApiResponse<List<Advertisement>> getVideosUrl(){
        ApiResponse<List<Advertisement>> apiResponse = advertisementService.getVideosUrl();
        return apiResponse;
    }
}

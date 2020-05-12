package com.justreading.onePanda.advertisement.service;

import com.justreading.onePanda.advertisement.entity.Advertisement;
import com.justreading.onePanda.common.ApiResponse;

import java.util.List;

/**
 * @author LYJ
 * @Description 读取云存储桶中的图片或者视频
 * @date 2020 年 02 月 28 日 21:19
 */
public interface AdvertisementService {
    /**
     * 获取云存储桶下图片的链接
     * @return
     */
    public ApiResponse<List<Advertisement>> getImagesUrl();

    /**
     * 获取云存储桶下视频的链接
     * @return
     */
    public ApiResponse<List<Advertisement>> getVideosUrl();
}

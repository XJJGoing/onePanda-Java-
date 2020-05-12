package com.justreading.onePanda.advertisement.service.impl;

import com.justreading.onePanda.advertisement.entity.Advertisement;
import com.justreading.onePanda.advertisement.service.AdvertisementService;
import com.justreading.onePanda.common.ApiResponse;
import com.justreading.onePanda.common.TenXunUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LYJ
 * @Description
 * @date 2020 年 02 月 28 日 21:20
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private TenXunUtil tenXunUtil;

    @Autowired
    private COSClient cosClient;

    @Override
    public ApiResponse<List<Advertisement>> getImagesUrl() {
        ApiResponse<List<Advertisement>> apiResponse = new ApiResponse<>();
        List<Advertisement> advertisementList = new ArrayList<>();
        String bucketName = tenXunUtil.getOnePandaBucket();
        String path = tenXunUtil.getPath();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix("advertisement/img/");
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(20);      //设置遍历的最大数
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                apiResponse.setMsg(e.getErrorMessage());
                apiResponse.setCode(Integer.parseInt(e.getErrorCode()));
                return apiResponse;
            } catch (CosClientException e) {
                apiResponse.setMsg(e.getMessage());
                e.printStackTrace();
                return apiResponse;
            }

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (int i = 1; i < cosObjectSummaries.size(); i++) {
                COSObjectSummary cosObjectSummary = cosObjectSummaries.get(i);
                Advertisement advertisement = new Advertisement();
                String key = cosObjectSummary.getKey();
                String url = path + key;
                advertisement.setUrl(url);
                advertisementList.add(advertisement);
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        apiResponse.setData(advertisementList);
        return apiResponse;
    }


    @Override
    public ApiResponse<List<Advertisement>> getVideosUrl() {
        ApiResponse<List<Advertisement>> apiResponse = new ApiResponse<>();
        List<Advertisement> advertisementList = new ArrayList<>();
        String bucketName = tenXunUtil.getOnePandaBucket();
        String path = tenXunUtil.getPath();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix("advertisement/video/");
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(20);      //设置遍历的最大数
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                apiResponse.setMsg(e.getErrorMessage());
                apiResponse.setCode(Integer.parseInt(e.getErrorCode()));
                return apiResponse;
            } catch (CosClientException e) {
                apiResponse.setMsg(e.getMessage());
                e.printStackTrace();
                return apiResponse;
            }

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (int i = 1; i < cosObjectSummaries.size(); i++) {
                COSObjectSummary cosObjectSummary = cosObjectSummaries.get(i);
                Advertisement advertisement = new Advertisement();
                String key = cosObjectSummary.getKey();
                String url = path + key;
                advertisement.setUrl(url);
                if(i == 1){
                    advertisement.setName("fake love");
                }else if(i == 2) {
                    advertisement.setName(" 防弹:永恒 ");
                }else if(i == 3){
                    advertisement.setName(" on ");
                }
                advertisementList.add(advertisement);
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        apiResponse.setCode(200);
        apiResponse.setMsg("查询成功");
        apiResponse.setData(advertisementList);
        return apiResponse;
    }
}

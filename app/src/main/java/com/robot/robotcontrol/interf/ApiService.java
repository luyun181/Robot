package com.robot.robotcontrol.interf;


import com.robot.robotcontrol.bean.CommonBean;
import com.robot.robotcontrol.bean.TokenBean;
import com.robot.robotcontrol.bean.UrlBean;
import com.robot.robotcontrol.bean.VideoListBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 2017/11/22.
 */

public interface ApiService {

    //登录
    @FormUrlEncoded
    @POST("login")
    Observable<CommonBean> getLongin(
            @Field("id") String id,
            @Field("password") String skill_id);


    //摄像头列表
    @GET("deviceList")
    Observable<VideoListBean> getVideoList();

    //摄像头控制
    @FormUrlEncoded
    @POST("ptzStart")
    Observable<CommonBean> getVideoControl(
            @Field("deviceSerial") String deviceSerial,
            @Field("direction") int direction,
            @Field("speed") int speed);
    //摄像头控制
    @FormUrlEncoded
    @POST("ptzStop")
    Observable<CommonBean> getStopControl(
            @Field("deviceSerial") String deviceSerial,
            @Field("direction") int direction);

    //token
    @GET("getToken")
    Observable<TokenBean> getToken();


    //url
    @GET("getEZOPEN")
    Observable<UrlBean> getUrl(@Query("deviceSerial")String deviceSerial);
}

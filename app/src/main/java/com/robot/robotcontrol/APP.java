package com.robot.robotcontrol;

import android.app.Application;

import com.videogo.openapi.EZOpenSDK;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /** * sdk日志开关，正式发布需要去掉 */
        EZOpenSDK.showSDKLog(true);
    /** * 设置是否支持P2P取流,详见api */
        EZOpenSDK.enableP2P(false);

    /** * APP_KEY请替换成自己申请的 */
        EZOpenSDK.initLib(this, "089c632132ea44b4b4bf82db781b9f8d");
    }
}

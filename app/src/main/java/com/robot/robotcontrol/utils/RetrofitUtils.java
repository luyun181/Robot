package com.robot.robotcontrol.utils;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.robot.robotcontrol.cookie.CookieJarImpl;
import com.robot.robotcontrol.cookie.store.PersistentCookieStore;
import com.robot.robotcontrol.interf.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/11/22.
 */

public class RetrofitUtils {
    public static String BASE_URL = "http://47.95.243.112:3432/";
    private static final int DEFAULT_TIMEOUT = 5;
    private static RetrofitUtils mRe;
    private Retrofit retrofit;
    private ApiService apiService;

    private RetrofitUtils(Context mContext) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        PersistentCookieStore persistentCookieStore = new PersistentCookieStore(mContext);
        CookieJarImpl cookieJarImpl = new CookieJarImpl(persistentCookieStore);
        httpBuilder.cookieJar(cookieJarImpl);
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
        OkHttpClient client = httpBuilder
                .cookieJar(cookieJar)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS) //设置超时
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(new StringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public synchronized static RetrofitUtils getInstance(Context mContext) {
        if (mRe == null) {
            mRe = new RetrofitUtils(mContext);
        }
        return mRe;
    }

    public <S> S create(Class<S> service) {
        return retrofit.create(service);
    }

    public static void desRetrofit() {
        if (mRe != null) {
            mRe = null;
        }
    }
}

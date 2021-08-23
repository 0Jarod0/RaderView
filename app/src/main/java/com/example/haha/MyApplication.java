package com.example.haha;

import android.app.Application;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.example.haha.customview.utils.SystemUtils;
import com.example.haha.hilt.HiltSimple;
import com.example.haha.paging3.pagingWithNet.RetrofitClient;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by haha on 2020/3/1.
 */

public class MyApplication extends Application {
    private static String TAG = MyApplication.class.getSimpleName();

    @Inject
    public HiltSimple hiltSimple;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();
        RetrofitClient.INSTANCE.init(retrofitBuilder -> retrofitBuilder
                .baseUrl("https://wanandroid.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson())));
        MultiDex.install(this);
    }

}

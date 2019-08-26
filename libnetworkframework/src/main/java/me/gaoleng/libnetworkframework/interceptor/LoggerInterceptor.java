package me.gaoleng.libnetworkframework.interceptor;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 日志拦截器
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public class LoggerInterceptor {
    public static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("---------->", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}

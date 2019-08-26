package me.gaoleng.libnetworkframework;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.gaoleng.libnetworkframework.interceptor.HeaderInterceptor;
import me.gaoleng.libnetworkframework.interceptor.LoggerInterceptor;
import me.gaoleng.libnetworkframework.util.Utils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口工厂类入口封装
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public class ApiFactory {
    private static HashMap<Config, RetrofitManager> retrofits = new HashMap<>();
    private static Context context;
    /**
     * 默认动态请求token
     */
    private static String defaultDynamicToken;

    private ApiFactory() {
        throw new UnsupportedOperationException("ApiFactory can not be called constructor");
    }

    /**
     * 根据初始化retrofit
     *
     * @param context 上下文，尽量使用application中的context，否则可能会造成内存泄露
     * @param configs 对应的配置
     */
    public static void init(Context context, Config... configs) {
        Utils.checkNotNull(context);
        Utils.checkNotNull(configs);
        if (configs.length == 0) {
            throw new IllegalArgumentException("At least one config is required");
        }

        ApiFactory.context = context;

        for (Config config : configs) {
            retrofits.put(config, new RetrofitManager(config));
        }
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getDefaultDynamicToken() {
        if (defaultDynamicToken == null) {
            defaultDynamicToken = Utils.getToken(context);
        }
        return defaultDynamicToken;
    }

    /**
     * 生成api
     *
     * @param clazz 需要生成的接口类
     * @param <T>   返回的接口类
     * @return 接口实例
     */
    public static <T> T getApi(Class<T> clazz) {
        final String key = clazz.toString();
        for (Map.Entry<Config, RetrofitManager> entry : retrofits.entrySet()) {
            if (entry.getValue().apis.containsKey(key)) {
                return (T) entry.getValue().apis.get(key);
            }
        }
        throw new RuntimeException("can not found " + clazz + ", maybe you are not called init function before getApi");
    }

    private static class RetrofitManager {
        private static final int DEFAULT_TIMEOUT = 15;
        private HashMap<String, Object> apis = new HashMap<>();
        private Retrofit retrofit;

        private RetrofitManager(Config config) {
            Utils.checkNotNull(config);

            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder
//                     .cookieJar(new CookieManger(App.getContext()))
                    .addInterceptor(new HeaderInterceptor(config))
                    .addInterceptor(LoggerInterceptor.getHttpLoggingInterceptor())
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(config.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();

            buildApis(config.getApis());
        }

        @SafeVarargs
        private final <T> void buildApis(Class<T>... clazzs) {
            Utils.checkNotNull(clazzs);

            for (Class<T> clazz : clazzs) {
                apis.put(clazz.toString(), retrofit.create(clazz));
            }
        }
    }
}

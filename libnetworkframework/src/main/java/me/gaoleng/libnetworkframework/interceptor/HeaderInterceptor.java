package me.gaoleng.libnetworkframework.interceptor;

import java.io.IOException;
import java.util.Set;

import me.gaoleng.libnetworkframework.ApiFactory;
import me.gaoleng.libnetworkframework.Config;
import me.gaoleng.libnetworkframework.util.Utils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头拦截器
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public class HeaderInterceptor implements Interceptor {
    private Config config;

    public HeaderInterceptor(Config config) {
        Utils.checkNotNull(config);

        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = addGeneralHeader(chain.request());
        return chain.proceed(request);
    }

    /**
     * 添加统一请求头
     *
     * @param request 请求体
     * @return Request
     */
    private Request addGeneralHeader(Request request) {
        Request.Builder builder = request.newBuilder();
        addDefaultHeader(builder);
        addCustomHeader(builder);
        return builder.build();
    }

    /**
     * 添加自定义的header
     *
     * @param builder header构造器
     */
    private void addCustomHeader(Request.Builder builder) {
        if (config.getHeaders() != null) {
            Set<String> keySet = config.getHeaders().keySet();
            for (String key : keySet) {
                builder.addHeader(key, config.getHeaders().get(key));
            }
        }
    }

    /**
     * 添加动态请求头，如token、timestamp
     *
     * @param builder header构造器
     */
    private void addDefaultHeader(Request.Builder builder) {
        if (!config.isUseDefaultHeaders()) return;

        builder.addHeader("token", ApiFactory.getDefaultDynamicToken());
        builder.addHeader("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        builder.addHeader("device_token", "CA5EC561E7B1AB62FD65F4C7A1B1EF066C333565731BC4FDF9505879142CCD55");
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
    }
}

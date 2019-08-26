package me.gaoleng.libnetworkframework;

import java.util.HashMap;

import lombok.Data;
import me.gaoleng.libnetworkframework.util.Utils;

/**
 * 网络请求配置项
 *
 * @author gaoleng
 * @date 2019-08-21
 */
@Data
public class Config {

    /**
     * 接口地址
     */
    private String baseUrl;

    /**
     * 请求接口类
     */
    private Class[] apis;

    /**
     * 使用默认headers
     * 包含Content-Type、token、device_token、timestamp
     */
    private boolean useDefaultHeaders;

    /**
     * 其他请求头，与{@link #useDefaultHeaders}相同的key，会替换掉
     */
    private HashMap<String, String> headers;

    private Config(Builder builder) {
        Utils.checkNotNull(builder.baseUrl);
        Utils.checkNotNull(builder.apis);
        if (builder.apis.length == 0) {
            throw new IllegalArgumentException("Apis must have at least one item");
        }

        baseUrl = builder.baseUrl;
        apis = builder.apis;
        useDefaultHeaders = builder.useDefaultHeaders;
        headers = builder.headers;
    }

    public static class Builder {
        private String baseUrl;
        private Class[] apis;
        private boolean useDefaultHeaders = true;
        private HashMap<String, String> headers;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setApis(Class... apis) {
            this.apis = apis;
            return this;
        }

        /**
         * 默认为true
         *
         * @param useDefaultHeaders 是否使用默认请求头
         * @return builder
         */
        public Builder setUseDefaultHeaders(boolean useDefaultHeaders) {
            this.useDefaultHeaders = useDefaultHeaders;
            return this;
        }

        public Builder setHeaders(HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String value) {
            if (this.headers == null) {
                this.headers = new HashMap<>();
            }
            this.headers.put(key, value);
            return this;
        }

        public Config build() {
            return new Config(this);
        }
    }
}

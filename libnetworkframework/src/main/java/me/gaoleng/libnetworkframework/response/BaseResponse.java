package me.gaoleng.libnetworkframework.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 接口返回基类
 *
 * @author gaoleng
 * @date 2019-08-21
 */
@Data
public class BaseResponse<T> {
    private T data;
    @SerializedName("err_resp")
    private ErrResp errResp;

    @Data
    class ErrResp {
        private String code;
        private String msg;
    }
}

package me.gaoleng.networkframeworkdemo.api;

import java.util.List;

import io.reactivex.Observable;
import me.gaoleng.libnetworkframework.response.BaseResponse;
import retrofit2.http.POST;

/**
 * It's description.
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public interface IMusicApi {

    @POST("recommend/dailysonglist")
    Observable<BaseResponse<List<Object>>> getDailySongList();
}

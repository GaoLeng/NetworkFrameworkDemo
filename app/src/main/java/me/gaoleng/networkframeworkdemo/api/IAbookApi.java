package me.gaoleng.networkframeworkdemo.api;

import io.reactivex.Observable;
import me.gaoleng.libnetworkframework.response.BaseResponse;
import retrofit2.http.POST;

/**
 * It's description.
 *
 * @author gaoleng
 * @date 2019-08-22
 */
public interface IAbookApi {

    @POST("playhistory/gethistory")
    Observable<BaseResponse<String>> getHistory();
}

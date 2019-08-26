package me.gaoleng.libnetworkframework.response;

import android.app.Dialog;
import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.gaoleng.libnetworkframework.widget.LoadingDialog;

/**
 * RxJava请求回调封装
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public abstract class GeneralObserver<T> implements Observer<BaseResponse<T>> {
    private Dialog loading;
    private boolean isShowLoading = false;

    /**
     * 默认构造器
     */
    public GeneralObserver() {
    }

    /**
     * 带loading的构造器
     *
     * @param context
     * @param isShowLoading
     */
    public GeneralObserver(Context context, boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
        loading = new LoadingDialog(context);
        loading.show();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> response) {
        if (response == null || response.getData() == null) {
            onError(new Exception("服务返回为空"));
            return;
        }
        BaseResponse<T>.ErrResp errResp = response.getErrResp();
        if (errResp != null && !"0".equals(errResp.getCode())) {
            onError(new Exception(errResp.getMsg()));
            return;
        }

        onSuccess(response.getData());
        onComplete();
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e.getMessage());
        onComplete();
    }

    @Override
    public void onComplete() {
        if (isShowLoading && loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

    /**
     * 请求失败时回调
     *
     * @param msg 接口返回错误消息
     */
    public abstract void onFailed(String msg);

    /**
     * 请求成功时回调
     *
     * @param data 接口返回数据
     */
    public abstract void onSuccess(T data);
}

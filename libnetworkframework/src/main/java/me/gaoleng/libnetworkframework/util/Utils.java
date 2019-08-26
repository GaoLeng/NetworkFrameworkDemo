package me.gaoleng.libnetworkframework.util;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * It's description.
 *
 * @author gaoleng
 * @date 2019-08-21
 */
public class Utils {
    /**
     * 统一线程处理（Observable)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static void checkNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException(o.getClass().getName() + " can not be null.");
        }
    }

    public static final String SP_NAME = "SP_NAME";
    public static final String TOKEN_KEY = "TOKEN_KEY";

    public static String getToken(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).getString(TOKEN_KEY, "");
    }
}

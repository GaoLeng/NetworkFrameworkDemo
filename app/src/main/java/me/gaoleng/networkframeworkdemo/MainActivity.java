package me.gaoleng.networkframeworkdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import me.gaoleng.libnetworkframework.Config;
import me.gaoleng.networkframeworkdemo.api.IAbookApi;
import me.gaoleng.networkframeworkdemo.api.IMusicApi;
import me.gaoleng.libnetworkframework.response.BaseResponse;
import me.gaoleng.libnetworkframework.response.GeneralObserver;
import me.gaoleng.libnetworkframework.ApiFactory;
import me.gaoleng.libnetworkframework.util.Utils;

public class MainActivity extends AppCompatActivity {

    private final static String ADDRESS = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApi();
    }

    private void initApi() {
        Config abookConfig = new Config.Builder()
                .setBaseUrl(ADDRESS + "voice/")
                .setApis(IAbookApi.class)
                .setUseDefaultHeaders(true)
                .build();

        Config musicConfig = new Config.Builder()
                .setBaseUrl(ADDRESS + "music/")
                .setApis(IMusicApi.class)
                .setUseDefaultHeaders(true)
                .build();

        ApiFactory.init(this, abookConfig, musicConfig);
    }


    public void onMusicClick(View v) {
        ApiFactory.getApi(IMusicApi.class)
                .getDailySongList()
                .compose(Utils.<BaseResponse<List<Object>>>compose())
                .subscribe(new GeneralObserver<List<Object>>(this, true) {
                    @Override
                    public void onSuccess(List<Object> data) {
                        Log.e("onMusicClick onSuccess", data.toString());
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.e("onMusicClick onFailed", msg);
                    }
                });
    }

    public void onAbookClick(View v) {
        ApiFactory.getApi(IAbookApi.class)
                .getHistory()
                .compose(Utils.<BaseResponse<String>>compose())
                .subscribe(new GeneralObserver<String>(this, true) {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("onAbookClick onSuccess", data);
                    }

                    @Override
                    public void onFailed(String msg) {
                        Log.e("onAbookClick onSuccess", msg);
                    }
                });
    }
}

package com.example.app.mvp.presenter;

import android.util.Log;


import com.example.app.base.BasePresenter;
import com.example.app.callback.RxObserverCallBack;
import com.example.app.mvp.model.RxOpretorImpl;
import com.example.app.mvp.model.constants.ApiConstants;
import com.example.app.mvp.ui.fragment.HomeFragment;

import java.io.IOException;

import okhttp3.ResponseBody;

public class HomeFrgPresenter extends BasePresenter {
    private RxOpretorImpl mImpl;
    private HomeFragment mHomeFragment;

    public HomeFrgPresenter(HomeFragment fragment) {
        mImpl = new RxOpretorImpl();
        this.mHomeFragment = fragment;
    }

    @Override
    public void start() {
        //2.向M层进行数据请求，请求到数据之后将数据返回给V层
        mImpl.rxGetRequest(ApiConstants.BAIDU_LIST, new RxObserverCallBack<Object>() {
            @Override
            public void onSuccessData(Object obj) {
                if (mHomeFragment != null) {
                    ResponseBody responseBody = (ResponseBody) obj;
                    try {
                        String string = responseBody.string();
                        //TODO gson解析
                        mHomeFragment.onScuccess(string);
                    } catch (IOException e) {
                        e.printStackTrace();
                        mHomeFragment.onError(e.getMessage());
                    }
                }
            }

            @Override
            public void onErrorMsg(String msg) {
                if (mHomeFragment != null) {
                    mHomeFragment.onError(msg);
                }
            }
        });
    }
}
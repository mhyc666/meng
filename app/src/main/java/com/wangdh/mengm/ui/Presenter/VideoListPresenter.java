package com.wangdh.mengm.ui.Presenter;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.wangdh.mengm.MyApplication;
import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.VideoData;
import com.wangdh.mengm.ui.contract.VideoListContract;
import com.wangdh.mengm.utils.ACache;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class VideoListPresenter extends RxPresenter<VideoListContract.View> implements VideoListContract.Presenter<VideoListContract.View> {
    private RetrofitManager api;

    @Inject
    public VideoListPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getVideolistData(String page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("showapi_appid", Constant.showapi_appid);
        map.put("showapi_sign", Constant.showapi_sign);
        map.put("type", "41");
        api.videoDataFlowable(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<VideoData>() {
                    @Override
                    public void onNext(VideoData data) {
                        if (data.getShowapi_res_code() == 0) {
                            mView.showVideolistData(data);
                        } else {
                            mView.showError("数据加载失败");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        mView.complete();
                    }
                });
    }

}

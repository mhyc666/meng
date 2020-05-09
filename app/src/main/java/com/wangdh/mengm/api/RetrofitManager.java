package com.wangdh.mengm.api;

import android.util.Log;

import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.bean.CelebratedDictum;
import com.wangdh.mengm.bean.CookBooksData;
import com.wangdh.mengm.bean.CookBookslistData;
import com.wangdh.mengm.bean.FunnyPicturesData;
import com.wangdh.mengm.bean.HeaderLayoutBean;
import com.wangdh.mengm.bean.HealthitemData;
import com.wangdh.mengm.bean.HealthitemListData;
import com.wangdh.mengm.bean.JokeData;
import com.wangdh.mengm.bean.MeizhiData;
import com.wangdh.mengm.bean.NewListData;
import com.wangdh.mengm.bean.VideoData;
import com.wangdh.mengm.bean.WeChatListData;
import com.wangdh.mengm.bean.WeatherAllData;
import com.wangdh.mengm.bean.WechatImage;

import org.reactivestreams.Subscription;

import java.util.Map;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * wdh.
 */

public class RetrofitManager {
    public static RetrofitManager instance;
    private ApiService service;

    public RetrofitManager(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constant.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static RetrofitManager getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new RetrofitManager(okHttpClient);
        return instance;
    }
    /**
     * 初始化通用的观察者
     * @param observable 观察者
     */
    public ResourceSubscriber startObservable(Flowable observable, ResourceSubscriber subscriber) {
        return (ResourceSubscriber)observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnLifecycle(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        Log.d("doOnLifecycle","OnSubscribe");
                    }
                }, new LongConsumer() {
                    @Override
                    public void accept(long t) throws Exception {
                        Log.d("doOnLifecycle","OnRequest");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("doOnLifecycle","OnCancel");
                    }
                })
                .subscribeWith(subscriber);
    }

    public Flowable<CelebratedDictum> splashRxjava() {
        return service.splashRxjava();
    }

    public Flowable<HeaderLayoutBean> headerRxjava(){
        return service.headerRxjava();
    }

    public Flowable<NewListData> newListDataFlowable(Map<String, String> params) {
        return service.newListDataRxjava(params);
    }

    public Flowable<WeChatListData> weChatListDataFlowable(Map<String, String> params) {
        return service.weCharListDataRxjava(params);
    }

    public Flowable<WechatImage> wechatImageFlowable() {
        return service.wecharImageRxjava();
    }

    public Flowable<CookBooksData> cookBooksDataFlowable(String key) {
        return service.cookbooksRxjava(key);
    }

    public Flowable<HealthitemData> healthitemDataFlowable() {
        return service.healthRxjava();
    }

    public Flowable<CookBookslistData> cookBookslistDataFlowable(Map<String, String> params) {
        return service.cookbooksListRxjava(params);
    }

    public Flowable<CookBookslistData> cookBooksSearchFlowable(Map<String, String> params){
        return service.cookbooksSearchRxjava(params);
    }

    public Flowable<JokeData> jokeDataFlowable(Map<String, String> params) {
        return service.jokeRxjava(params);
    }

    public Flowable<FunnyPicturesData> funnyPicturesFlowable(String type, Map<String, String> params) {
        return service.FunnyPicturesRxjava(type, params);
    }

    public Flowable<MeizhiData> meizhiDataFlowable(String type,int page){
        return service.MeiZhiRxjava(type,page);
    }

    public Flowable<VideoData> videoDataFlowable(Map<String, String> params){
        return service.videoRxjava(params);
    }

    public Flowable<HealthitemListData> healthitemListDataFlowable(Map<String, String> params){
        return service.healthListRxjava(params);
    }

    public Flowable<WeatherAllData> weatherAllDataFlowable(Map<String, String> params){
        return service.weatherRxjava(params);
    }
}

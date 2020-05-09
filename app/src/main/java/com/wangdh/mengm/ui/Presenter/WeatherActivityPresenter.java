package com.wangdh.mengm.ui.Presenter;


import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.WeatherAllData;
import com.wangdh.mengm.ui.contract.WeatherActivityContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class WeatherActivityPresenter extends RxPresenter<WeatherActivityContract.View> implements WeatherActivityContract.Presenter<WeatherActivityContract.View>{
    private RetrofitManager api;
    @Inject
    public WeatherActivityPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getWeatherData(String city) {
        Map<String, String> map = new HashMap<>();
        map.put("city",city);
        map.put("key", Constant.weatherKey);
        api.weatherAllDataFlowable(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WeatherAllData>() {
                    @Override
                    public void onNext(WeatherAllData weatherAllData) {
                        if (!weatherAllData.getHeWeather5().get(0).getStatus().equals("unknown city")) {
                            try {
                                mView.showWeatherData(weatherAllData);
                            }catch (NullPointerException e){
                                e.getMessage();
                            }

                        } else {
                            mView.showError("没有这个城市");
                        }
                    }
                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        try {
                            mView.complete();
                        }catch (NullPointerException e){
                            e.getMessage();
                        }
                    }
                });
    }
}

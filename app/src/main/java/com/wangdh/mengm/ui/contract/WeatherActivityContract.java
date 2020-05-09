package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.WeatherAllData;

public interface WeatherActivityContract {
    interface View extends BaseContract.BaseView{
       void showWeatherData(WeatherAllData data);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getWeatherData(String city);
    }
}

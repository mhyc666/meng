package com.wangdh.mengm.ui.contract;


import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.HealthitemData;

public interface HealthFragmentContract {
    interface View extends BaseContract.BaseView {
        void showHealthData(HealthitemData data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getHealthData();
    }
}

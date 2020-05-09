package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.HealthitemListData;

public interface HealthListContract {
    interface View extends BaseContract.BaseView{
        void showHealthListData(HealthitemListData data);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getHealthListData(String id,String page);
    }
}

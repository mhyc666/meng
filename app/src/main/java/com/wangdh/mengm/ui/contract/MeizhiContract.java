package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.MeizhiData;

public interface MeizhiContract {
    interface View extends BaseContract.BaseView{
        void showMeizhiData(MeizhiData data);

    }
    interface Presenter<T> extends BaseContract.BasePresenter<T>{
       void getMeiZhiData(String type,int page);
    }
}

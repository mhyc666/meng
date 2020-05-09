package com.wangdh.mengm.ui.contract;


import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.CelebratedDictum;
import com.wangdh.mengm.bean.HeaderLayoutBean;

public interface SplashContract{
    interface View extends BaseContract.BaseView{
        void showSplashData(CelebratedDictum dictum);
        void showHeaderData(HeaderLayoutBean headerLayoutBean);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getSplashDta();
        void getHeaderData();
    }
}

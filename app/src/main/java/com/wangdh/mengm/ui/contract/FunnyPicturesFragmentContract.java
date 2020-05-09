package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.FunnyPicturesData;

public interface FunnyPicturesFragmentContract {
    interface View extends BaseContract.BaseView {
        void showFunnyPicturesData(FunnyPicturesData data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getFunnyPicturesData(String type,String page);
    }
}

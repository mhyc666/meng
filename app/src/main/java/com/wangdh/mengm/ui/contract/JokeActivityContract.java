package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.JokeData;

public interface JokeActivityContract {
    interface View extends BaseContract.BaseView {
        void showJokedata(JokeData data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getJokedata(int page);
    }
}

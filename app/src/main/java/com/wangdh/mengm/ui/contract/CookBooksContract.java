package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.CookBooksData;

public interface CookBooksContract {
    interface View extends BaseContract.BaseView {
        void showCookBooksData(CookBooksData data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCookBooksData();
    }
}

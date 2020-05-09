
package com.wangdh.mengm.base;


public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showError(String s);

        void complete();

    }
}

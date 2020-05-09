package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;

public interface RegisterContract {

    interface View extends BaseContract.BaseView{

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getRegister(String name,String password,String phone);
    }
}

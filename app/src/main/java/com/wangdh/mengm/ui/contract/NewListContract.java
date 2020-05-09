package com.wangdh.mengm.ui.contract;


import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.NewListData;

public interface NewListContract {
    interface View extends BaseContract.BaseView{
        void showNewlistData(NewListData data);
    }
    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getNewlistData(String type,String num,String start);
    }
}

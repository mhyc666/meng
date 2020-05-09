package com.wangdh.mengm.ui.contract;


import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.CookBookslistData;

public interface CookBooksListContract {
    interface View extends BaseContract.BaseView{
        void showCookBooksListData(CookBookslistData data);

    }
    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCookBooksListData(String classid,String num,String start);
    }
}

package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.CookBookslistData;

public interface SearchCoolBookContract {
    interface View extends BaseContract.BaseView {

        void showSearchResultList(CookBookslistData list);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getSearchResultList(String query);

    }
}

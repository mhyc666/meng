package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.VideoData;

public interface VideoListContract {
    interface View extends BaseContract.BaseView {
        void showVideolistData(VideoData data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getVideolistData(String page);
    }
}

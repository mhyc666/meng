package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.WechatImage;


public interface WeChatFragmentContract {
    interface View extends BaseContract.BaseView{
        void showImageData(WechatImage data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getImageData();
    }
}

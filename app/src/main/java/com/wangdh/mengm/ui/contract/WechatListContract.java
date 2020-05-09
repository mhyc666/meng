package com.wangdh.mengm.ui.contract;

import com.wangdh.mengm.base.BaseContract;
import com.wangdh.mengm.bean.WeChatListData;

import java.util.List;

/**
 * wdh
 */

public interface WechatListContract {
    interface View extends BaseContract.BaseView{
        void showWechatlistDta(WeChatListData data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getWechatlistDta(String type,int page);
    }
}

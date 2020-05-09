package com.wangdh.mengm.ui.Presenter;

import android.util.Log;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.WeChatListData;
import com.wangdh.mengm.ui.contract.WechatListContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

/**
 *wdh
 */

public class WechatListPresenter extends RxPresenter<WechatListContract.View>implements WechatListContract.Presenter<WechatListContract.View>{
    private RetrofitManager api;
    @Inject
    public WechatListPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getWechatlistDta(String type,int page) {
        Map<String,String> map=new HashMap<>();
        map.put("typeId",type);
        map.put("page", String.valueOf(page));
        map.put("showapi_appid",Constant.showapi_appid);
        map.put("showapi_sign",Constant.showapi_sign);
     api.weChatListDataFlowable(map).subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(new DefaultSubscriber<WeChatListData>() {
                 @Override
                 public void onNext(WeChatListData data) {
                     if(data.getShowapi_res_code()==0) {
                         try {
                             mView.showWechatlistDta(data);
                         }catch (NullPointerException e){
                             Log.i("wechat","NullPointerException");
                         }

                     }else {
                         mView.showError("数据加载失败");
                     }
                 }

                 @Override
                 public void onError(Throwable t) {
                    mView.showError(t.getMessage());
                 }

                 @Override
                 public void onComplete() {
                   mView.complete();
                 }
             });
    }
}

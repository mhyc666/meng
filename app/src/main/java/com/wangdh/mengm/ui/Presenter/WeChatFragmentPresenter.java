package com.wangdh.mengm.ui.Presenter;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.WechatImage;
import com.wangdh.mengm.ui.contract.WeChatFragmentContract;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class WeChatFragmentPresenter extends RxPresenter<WeChatFragmentContract.View> implements WeChatFragmentContract.Presenter<WeChatFragmentContract.View>{
   private RetrofitManager api;
    @Inject
    public WeChatFragmentPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getImageData() {
        api.wechatImageFlowable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<WechatImage>() {
                    @Override
                    public void onNext(WechatImage wechatImage) {
                        mView.showImageData(wechatImage);
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

package com.wangdh.mengm.ui.Presenter;

import android.util.Log;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.CelebratedDictum;
import com.wangdh.mengm.bean.HeaderLayoutBean;
import com.wangdh.mengm.ui.contract.SplashContract;
import javax.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.ResourceSubscriber;

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {
    private RetrofitManager api;

    @Inject
    public SplashPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getSplashDta() {
        api.splashRxjava().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<CelebratedDictum>() {
                    @Override
                    public void onNext(CelebratedDictum dictum) {
                        if (dictum.getError_code() == 0) {
                            try {
                                mView.showSplashData(dictum);
                            }catch (NullPointerException e){
                                e.getMessage();
                            }

                        } else {
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage() + "");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getHeaderData() {
        Flowable<HeaderLayoutBean> flowable = api.headerRxjava();
        ResourceSubscriber resourceSubscriber = new ResourceSubscriber<HeaderLayoutBean>() {

            @Override
            public void onNext(HeaderLayoutBean headerLayoutBean) {
                if (headerLayoutBean.getError_code() == 0) {
                    mView.showHeaderData(headerLayoutBean);
                } else {
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.i("toast","head:"+t.getMessage());
            }

            @Override
            public void onComplete() {
                mView.complete();

            }
        };
        addSubscrebe(api.startObservable(flowable, resourceSubscriber));
    }

    //                        if (t instanceof HttpException) {
//                            HttpException httpException = (HttpException) t;
//                            //httpException.response().errorBody().string()
//                            int code = httpException.code();
//                            String msg = httpException.getMessage();
//                            if (code == 504) {
//                                mView.showError("网络不给力 "+msg);
//                            }
//                            mView.showError(code+msg);
//                            //apiCallback.onFailure(code, msg);
//                        } else {
//                            mView.showError("数据加载失败");
//                            //apiCallback.onFailure(0, e.getMessage());
//                        }
}

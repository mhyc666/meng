package com.wangdh.mengm.ui.Presenter;

import android.util.Log;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.MeizhiData;
import com.wangdh.mengm.ui.contract.MeizhiContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class MeizhiPresenter extends RxPresenter<MeizhiContract.View> implements MeizhiContract.Presenter<MeizhiContract.View> {
    private RetrofitManager api;

    @Inject
    public MeizhiPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getMeiZhiData(String type, int page) {
        api.meizhiDataFlowable(type, page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<MeizhiData>() {
                    @Override
                    public void onNext(MeizhiData data) {
                        try {
                            mView.showMeizhiData(data);
                        }catch (Exception e){
                            Log.i("toast","Meizhi:"+e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        if(mView!=null){
                            mView.complete();
                        }
                    }
                });
    }
}

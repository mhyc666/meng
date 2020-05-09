package com.wangdh.mengm.ui.Presenter;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.FunnyPicturesData;
import com.wangdh.mengm.ui.contract.FunnyPicturesFragmentContract;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class FunnyPicturesPresenter extends RxPresenter<FunnyPicturesFragmentContract.View>
        implements FunnyPicturesFragmentContract.Presenter<FunnyPicturesFragmentContract.View> {
    private RetrofitManager api;

    @Inject
    public FunnyPicturesPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getFunnyPicturesData(String type, String page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("showapi_appid", Constant.showapi_appid);
        map.put("showapi_sign", Constant.showapi_sign);
        api.funnyPicturesFlowable(type, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<FunnyPicturesData>() {
                    @Override
                    public void onNext(FunnyPicturesData funnyPicturesData) {
                        if (funnyPicturesData.getShowapi_res_code() == 0) {
                            try{
                                mView.showFunnyPicturesData(funnyPicturesData);
                            }catch (NullPointerException e){
                                e.getMessage();
                            }

                        } else {
                            mView.showError("数据加载失败");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.showError(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        try{
                            mView.complete();
                        }catch (NullPointerException e){
                            e.getMessage();
                        }
                    }
                });
    }
}

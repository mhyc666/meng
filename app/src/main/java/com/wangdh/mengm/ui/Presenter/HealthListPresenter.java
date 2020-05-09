package com.wangdh.mengm.ui.Presenter;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.HealthitemListData;
import com.wangdh.mengm.ui.contract.HealthListContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class HealthListPresenter extends RxPresenter<HealthListContract.View> implements HealthListContract.Presenter<HealthListContract.View> {
    private RetrofitManager api;

    @Inject
    public HealthListPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getHealthListData(String id, String page) {
        Map<String, String> map = new HashMap<>();
        map.put("tid",id);
        map.put("page", page);
        map.put("showapi_appid", Constant.showapi_appid);
        map.put("showapi_sign", Constant.showapi_sign);
        api.healthitemListDataFlowable(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<HealthitemListData>() {
                    @Override
                    public void onNext(HealthitemListData data) {
                        if(data.getShowapi_res_code()==0&&data.getShowapi_res_body()!=null){
                            try {
                                mView.showHealthListData(data);
                            }catch (NullPointerException e){
                                e.getMessage();
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
                        try {
                            mView.complete();
                        }catch (NullPointerException e){
                            e.getMessage();
                        }
                    }
                });
    }
}

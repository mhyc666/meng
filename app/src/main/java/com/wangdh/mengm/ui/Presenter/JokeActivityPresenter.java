package com.wangdh.mengm.ui.Presenter;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.JokeData;
import com.wangdh.mengm.ui.contract.JokeActivityContract;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class JokeActivityPresenter extends RxPresenter<JokeActivityContract.View> implements
        JokeActivityContract.Presenter<JokeActivityContract.View> {
    private RetrofitManager api;

    @Inject
    public JokeActivityPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getJokedata(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(page));
        map.put("showapi_appid", Constant.showapi_appid);
        map.put("showapi_sign", Constant.showapi_sign);
        api.jokeDataFlowable(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<JokeData>() {
                    @Override
                    public void onNext(JokeData data) {
                        if(data.getShowapi_res_code()==0){
                            try {
                                mView.showJokedata(data);
                            }catch (NullPointerException e){

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
                        if(mView!=null){
                            mView.complete();
                        }
                    }
                });
    }
}

package com.wangdh.mengm.ui.Presenter;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.CookBookslistData;
import com.wangdh.mengm.ui.contract.SearchCoolBookContract;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class SeaechCookBooksPresenter extends RxPresenter<SearchCoolBookContract.View>implements SearchCoolBookContract.Presenter<SearchCoolBookContract.View>{
    private RetrofitManager api;
    @Inject
    public SeaechCookBooksPresenter(RetrofitManager api) {
        this.api = api;
    }
    @Override
    public void getSearchResultList(String query) {
        Map<String, String> map = new HashMap<>();
        map.put("keyword", query);
        map.put("num", "20");
        map.put("appkey", Constant.jcloudKey);
        api.cookBooksSearchFlowable(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<CookBookslistData>() {
                    @Override
                    public void onNext(CookBookslistData cookBookslistData) {
                        if (cookBookslistData.getCode().equals("10000")){
                            mView.showSearchResultList(cookBookslistData);
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

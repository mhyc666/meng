package com.wangdh.mengm.ui.Presenter;

import com.wangdh.mengm.api.RetrofitManager;
import com.wangdh.mengm.base.Constant;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.NewListData;
import com.wangdh.mengm.ui.contract.NewListContract;
import com.wangdh.mengm.utils.RxUtils;
import com.wangdh.mengm.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DefaultSubscriber;

public class NewListPresenter extends RxPresenter<NewListContract.View> implements NewListContract.Presenter<NewListContract.View> {
    private RetrofitManager api;

    @Inject
    public NewListPresenter(RetrofitManager api) {
        this.api = api;
    }

    @Override
    public void getNewlistData(String type, String num, String start) {
        Map<String, String> map = new HashMap<>();
        map.put("channel", type);
        map.put("num", num);
        map.put("start", start);
        map.put("appkey", Constant.jcloudKey);

        String key = StringUtils.creatAcacheKey("new-review-list", type, num, start);
        Flowable<NewListData> flowable = api.newListDataFlowable(map).compose(RxUtils.<NewListData>rxCacheBeanHelper(key));
        Flowable.concat(RxUtils.rxCreateDiskFlowable(key, NewListData.class), flowable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<NewListData>() {
                    @Override
                    public void onNext(NewListData newListDatas) {
                        if (newListDatas.getCode().equals("10000")) {
                            mView.showNewlistData(newListDatas);
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
                        mView.complete();
                    }
                });
        }

//        api.newListDataFlowable(map).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultSubscriber<NewListData>() {
//                    @Override
//                    public void onNext(NewListData newListDatas) {
//                        if (newListDatas.getCode().equals("10000")) {
//                            mView.showNewlistData(newListDatas);
//                        } else {
//                            mView.showError("数据加载失败");
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//                        mView.showError(t.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        mView.complete();
//                    }
//                });
}
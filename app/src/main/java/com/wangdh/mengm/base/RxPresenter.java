
package com.wangdh.mengm.base;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    private Subscription subscription;
    private CompositeDisposable mDisposables;


    protected void addSubscrebe(Disposable disposable) {
        if (disposable == null) return;
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }


    protected void unSubscribe(Disposable disposable){
        if(mDisposables!=null) {
            mDisposables.delete(disposable);
        }
//        if(subscription!=null){
//            subscription.cancel();
//        }
    }

    //取消所有的订阅
    public void dispose(){
        if(mDisposables!=null){
            mDisposables.clear();
        }
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        unSubscribe(mDisposables);
        this.mView = null;
    }
}

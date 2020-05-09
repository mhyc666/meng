package com.wangdh.mengm.ui.Presenter;

import android.util.Log;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wangdh.mengm.base.RxPresenter;
import com.wangdh.mengm.bean.bmob.AccountBean;
import com.wangdh.mengm.ui.contract.RegisterContract;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter<RegisterContract.View> {


    @Override
    public void getRegister(String name, String password, String phone) {
        AccountBean bean = new AccountBean();
        bean.setName(name);
        bean.setPassword(password);
        bean.setPhone(phone);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(name, password);//同步方法
                    bean.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                mView.complete();
                            } else {
                                if (e.getMessage().equals("unique index cannot has duplicate value: " + name)) {
                                    mView.showError("您输入的账户已被注册");
                                } else {
                                    mView.showError("注册失败");
                                }
                                Log.i("toast", "失败" + e.getMessage() + "+code+:" + e.getErrorCode());
                            }
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    int errorCode = e.getErrorCode();
                    if (errorCode == EMError.NETWORK_ERROR) {
                        mView.showError("网络异常，请检查网络！");
                    } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                        mView.showError("用户已存在");
                    } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                        mView.showError("注册失败，无权限");
                    } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                        mView.showError("用户名不合法");
                    } else {
                        mView.showError("注册失败");
                    }
                }
            }
        }).start();
    }
}

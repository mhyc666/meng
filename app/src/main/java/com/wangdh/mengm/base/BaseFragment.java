package com.wangdh.mengm.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wangdh.mengm.MyApplication;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.widget.loadding.CustomDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private View mContentView;
    protected FragmentActivity activity;
    protected Context mContext;
    private Unbinder unbinder;
    private CustomDialog dialog;//进度条
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(), container, false);
        unbinder = ButterKnife.bind(this, mContentView);
        activity = getSupportActivity();
        mContext = activity;
        setupActivityComponent(MyApplication.getsInstance().getAppComponent());
        init();//在初始化视图之前，做的一些操作
        initData();
        initView();
        return mContentView;
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected abstract int setLayoutResourceID();

    protected abstract void initData();

    protected abstract void initView();

    protected void init() {
    }
    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(getActivity());
            dialog.setCancelable(true);
            //点击dialog之外的区域可以取消dialog
            dialog.setCanceledOnTouchOutside(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    protected void toast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        dismissDialog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }
}

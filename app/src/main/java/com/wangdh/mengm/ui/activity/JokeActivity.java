package com.wangdh.mengm.ui.activity;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.JokeData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.JokeActivityPresenter;
import com.wangdh.mengm.ui.adapter.JokeAdapter;
import com.wangdh.mengm.ui.contract.JokeActivityContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class JokeActivity extends BaseActivity implements JokeActivityContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<JokeData.ShowapiResBodyBean.ContentlistBean> mData = new ArrayList<>();
    @Inject
    JokeActivityPresenter mPresenter;
    private JokeAdapter adapter;
    private int page = 1;
    private View errorView;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initView() {
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            mData.clear();
            page = 1;
            mPresenter.getJokedata(page);
        });
        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, "笑话", this);
        adapter = new JokeAdapter(mData);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this, recycler);
        RecyclerViewUtil.StaggeredGridinit(recycler, adapter);
        fab.setOnClickListener(v -> recycler.scrollToPosition(0));

        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {
            mSwipe.setRefreshing(true);
            mPresenter.getJokedata(page);
        });
    }

    @Override
    protected void initData() {
        setDataRefresh(true);
        mPresenter.attachView(this);
        mPresenter.getJokedata(page);
    }

    @Override
    public void showError(String s) {
        setDataRefresh(false);
        adapter.loadMoreEnd();
        toast(s);
        adapter.setEmptyView(getErrorView());
    }

    @Override
    public void complete() {
        setDataRefresh(false);
    }

    @Override
    public void showJokedata(JokeData data) {
        mData.addAll(data.getShowapi_res_body().getContentlist());
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
    }

    private void setDataRefresh(boolean refresh) {
        if (refresh) {
            mSwipe.setRefreshing(refresh);
        } else {
            new Handler().postDelayed(() -> {
                try {
                    mSwipe.setRefreshing(refresh);
                } catch (NullPointerException e) {
                    e.getMessage();
                }

            }, 800);//延时消失加载的loading
        }
    }
    public View getErrorView() {
        return errorView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mData.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(recycler.getContext())) {
                    page = page + 1;
                    mPresenter.getJokedata(page);
                } else {
                    //获取更多数据失败
                    adapter.loadMoreFail();
                }
            }, 1000);
        } else {
            adapter.loadMoreEnd();
        }
    }
}

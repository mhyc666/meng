package com.wangdh.mengm.ui.activity;

import android.content.Intent;
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
import com.wangdh.mengm.bean.HealthitemListData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.HealthListPresenter;
import com.wangdh.mengm.ui.adapter.HealthListAdapter;
import com.wangdh.mengm.ui.contract.HealthListContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;
import com.wangdh.mengm.utils.ToolbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class HealthListActivity extends BaseActivity implements HealthListContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private String id, name;
    private int page = 1;
    @Inject
    HealthListPresenter mPresenter;
    private HealthListAdapter adapter;
    private List<HealthitemListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean> itemData = new ArrayList<>();
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
            itemData.clear();
            page = 1;
            mPresenter.getHealthListData(id, String.valueOf(page));
        });
        setDataRefresh(true);

        ToolbarUtils.initTitle(toolbar, R.mipmap.ab_back, "健康." + name, this);
        adapter = new HealthListAdapter(itemData);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this, recycler);
        RecyclerViewUtil.init(this, recycler, adapter);
        fab.setOnClickListener(v -> recycler.scrollToPosition(0));
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            String url = adapter.getItem(position).getWapurl();
            Intent intent = new Intent(this, WebViewDetailsActivity.class);
            intent.putExtra("wechaturl", url);
            startActivity(intent);
        });
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {
            setDataRefresh(true);
            mPresenter.getHealthListData(id, String.valueOf(page));
        });
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("tid");
        name = getIntent().getStringExtra("name");
        mPresenter.attachView(this);
        mPresenter.getHealthListData(id, String.valueOf(page));
    }

    @Override
    public void showError(String s) {
        toast(s);
        setDataRefresh(false);
        adapter.setEmptyView(getErrorView());
    }

    @Override
    public void complete() {
        setDataRefresh(false);
    }

    @Override
    public void showHealthListData(HealthitemListData data) {
        if (data.getShowapi_res_body().getPagebean().getContentlist() != null) {
            itemData.addAll(data.getShowapi_res_body().getPagebean().getContentlist());
            adapter.notifyDataSetChanged();
            adapter.loadMoreComplete();
        } else {
            toast("数据加载失败");
        }
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
                }, 800); //延时消失加载的loading
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (itemData.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(recycler.getContext())) {
                    page = page + 1;
                    mPresenter.getHealthListData(id, String.valueOf(page));
                } else {
                    adapter.loadMoreFail();
                }
            }, 1000);
        } else {
            adapter.loadMoreEnd();
        }
    }

    public View getErrorView() {
        return errorView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

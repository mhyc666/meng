package com.wangdh.mengm.ui.activity;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangdh.mengm.R;
import com.wangdh.mengm.base.BaseActivity;
import com.wangdh.mengm.bean.WeChatListData;
import com.wangdh.mengm.component.AppComponent;
import com.wangdh.mengm.component.DaggerActivityComponent;
import com.wangdh.mengm.ui.Presenter.WechatListPresenter;
import com.wangdh.mengm.ui.adapter.WechatListAdapter;
import com.wangdh.mengm.ui.contract.WechatListContract;
import com.wangdh.mengm.utils.NetworkUtil;
import com.wangdh.mengm.utils.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * 微信文章列表
 */

public class WeChatListActivity extends BaseActivity implements WechatListContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.back)
    ImageView iv;
    private String type;
    @Inject
    WechatListPresenter mPresenter;
    private List<WeChatListData.ShowapiResBodyBean.PagebeanBean.ContentlistBean> itemdata = new ArrayList<>();
    private WechatListAdapter adapter;
    private int i = 1;
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
        return R.layout.activity_wechatlist;
    }

    @Override
    protected void initView() {
        mSwipe.setColorSchemeResources(R.color.colorPrimaryDark2, R.color.btn_blue, R.color.ywlogin_colorPrimaryDark);//设置进度动画的颜色
        mSwipe.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipe.setOnRefreshListener(() -> {
            itemdata.clear();
            i = 1;
            mPresenter.getWechatlistDta(type, i);
        });
        adapter = new WechatListAdapter(itemdata);
        RecyclerViewUtil.StaggeredGridinit(recycler, adapter);
        adapter.setOnLoadMoreListener(this, recycler);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            String url = itemdata.get(position).getUrl();
            Intent intent = new Intent(WeChatListActivity.this, WebViewDetailsActivity.class);
            intent.putExtra("wechaturl", url);
            startActivity(intent);
        });

        mFab.setOnClickListener(v -> recycler.scrollToPosition(0));
        iv.setOnClickListener(v -> {
            if ((mPresenter != null)) {
                mPresenter.detachView();
            }
            finish();
        });

        errorView = LayoutInflater.from(getContext()).inflate(R.layout.error_view, (ViewGroup) recycler.getParent(), false);
        errorView.setOnClickListener(v -> {
            mSwipe.setRefreshing(true);
            mPresenter.getWechatlistDta(type, i);
        });
    }

    @Override
    protected void initData() {
        showDialog();
        type = getIntent().getStringExtra("wechattype");
        mPresenter.attachView(this);
        mPresenter.getWechatlistDta(type, i);
    }

    @Override
    public void showError(String s) {
        hideDialog();
        mSwipe.setRefreshing(false);
        adapter.loadMoreEnd();
        adapter.setEmptyView(getErrorView());
        toast(s);
    }

    @Override
    public void complete() {
        try {
            mSwipe.setRefreshing(false);
        }catch (NullPointerException e){
            e.getMessage();
        }
        adapter.loadMoreComplete();
        hideDialog();
    }

    @Override
    public void showWechatlistDta(WeChatListData data) {
        itemdata.addAll(data.getShowapi_res_body().getPagebean().getContentlist());
        adapter.notifyDataSetChanged();
    }

    public View getErrorView() {
        return errorView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLoadMoreRequested() {
        if (itemdata.size() >= 20) {
            recycler.postDelayed(() -> {
                if (NetworkUtil.isAvailable(getContext())) {
                    i = i + 1;
                    mPresenter.getWechatlistDta(type, i);
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
